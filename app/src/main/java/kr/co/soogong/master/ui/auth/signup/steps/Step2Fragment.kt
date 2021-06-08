package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep2Binding
import kr.co.soogong.master.ui.auth.signup.LimitTime
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.TimerTerm
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.utils.PhoneNumberHelper
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class Step2Fragment : BaseFragment<FragmentSignUpStep2Binding>(
    R.layout.fragment_sign_up_step2
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private val timer = object: CountDownTimer(LimitTime * 1000, TimerTerm){
        override fun onTick(millisUntilFinished: Long) {
            binding.timerView.text = "${millisUntilFinished/1000/60}:${millisUntilFinished/1000%60}"
        }

        override fun onFinish() {
            binding.rightButton.isEnabled = true
            binding.alertExpiredCertificationTime.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()

        viewModel.auth = Firebase.auth
        initFirebaseAuthCallbacks()
        startPhoneNumberVerification()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            requestCertificationCodeAgainText.setOnClickListener {
                resendVerificationCode()
            }

            leftButton.setOnClickListener {
                (activity as? SignUpActivity)?.moveToPrevious()
            }

            rightButton.setOnClickListener {
                viewModel.certificationCode.observe(viewLifecycleOwner, {
                    alertInvalidCertificationCode.isVisible = it.length < 6
                    if(alertWrongCertificationCode.isVisible) alertWrongCertificationCode.isVisible = false
                })

                if(!alertWrongCertificationCode.isVisible && !alertExpiredCertificationTime.isVisible && !alertInvalidCertificationCode.isVisible){
                    verifyPhoneNumberWithCode()
                }
            }
        }
    }

    private fun startTimer() {
        binding.alertExpiredCertificationTime.visibility = View.GONE
        timer.start()
    }

    private fun stopTimer() {
        timer.cancel()
    }

    private fun initFirebaseAuthCallbacks() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Timber.tag(TAG).d("onVerificationCompleted: $credential")
                viewModel.phoneAuthCredential.value = credential
                (activity as? SignUpActivity)?.moveToNext()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Timber.tag(TAG).d("onVerificationFailed: $e")

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    binding.alertInvalidCertificationCode.isVisible = true
                    return
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    requireContext().toast(getString(R.string.firebase_auth_too_many_requests))
                    return
                }
                // other errors
                requireContext().toast(getString(R.string.error_message_of_request_failed))
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Timber.tag(TAG).d("onCodeSent: $verificationId, $token")
                requireContext().toast(getString(R.string.certification_code_requested))
                binding.rightButton.isEnabled = true

                viewModel.storedVerificationId.value = verificationId
                viewModel.resendToken.value = token
            }
        }
    }

    private fun startPhoneNumberVerification() {
        Timber.tag(TAG).d("startPhoneNumberVerification: ${viewModel.phoneNumber.value}")

        startTimer()
        viewModel.phoneNumber.value?.let { phoneNumber ->
            val options = PhoneAuthOptions.newBuilder(viewModel.auth)
                .setPhoneNumber(PhoneNumberHelper.toGlobalNumber(phoneNumber))                                    // Phone number to verify
                .setTimeout(LimitTime, TimeUnit.SECONDS)                        // Timeout and unit
                .setActivity(requireActivity())                                 // Activity (for callback binding)
                .setCallbacks(callbacks)                                        // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun verifyPhoneNumberWithCode() {
        Timber.tag(TAG).d("verifyPhoneNumberWithCode: ${viewModel.storedVerificationId.value}, ${viewModel.certificationCode.value}")

        viewModel.storedVerificationId.value?.let{ verificationId ->
            viewModel.certificationCode.value?.let { code ->
                viewModel.phoneAuthCredential.value = PhoneAuthProvider.getCredential(verificationId, code)
                signInWithPhoneAuthCredential()
            }
        }
    }

    private fun resendVerificationCode() {
        Timber.tag(TAG).d("resendVerificationCode: ${viewModel.phoneNumber.value}, ${viewModel.resendToken.value}")

        startTimer()
        viewModel.phoneNumber.value?.let { phoneNumber ->
            val optionsBuilder = PhoneAuthOptions.newBuilder(viewModel.auth)
                .setPhoneNumber(PhoneNumberHelper.toGlobalNumber(phoneNumber))
                .setTimeout(LimitTime, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
            if (viewModel.resendToken.value != null) {
                optionsBuilder.setForceResendingToken(viewModel.resendToken.value!!) // callback's ForceResendingToken
            }
            PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
        }
    }

    private fun signInWithPhoneAuthCredential() {
        Timber.tag(TAG).d("resendVerificationCode: ${viewModel.phoneAuthCredential.value}")

        viewModel.phoneAuthCredential.value?.let { credential ->
            viewModel.auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.tag(TAG).d("signInWithCredential successfully: ")
                        (activity as? SignUpActivity)?.moveToNext()
                    } else {
                        // Sign in failed, display a message and update the UI
                        Timber.tag(TAG).d("signInWithCredential failed: ${task.exception}")

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            binding.alertWrongCertificationCode.isVisible = true
                        } else {
                            requireContext().toast(getString(R.string.error_message_of_request_failed))
                        }
                    }
                }
        }
    }

    companion object {
        private const val TAG = "Step2Fragment"

        fun newInstance(): Step2Fragment {
            return Step2Fragment()
        }
    }
}