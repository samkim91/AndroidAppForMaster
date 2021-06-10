package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep2Binding
import kr.co.soogong.master.ui.auth.signup.LimitTime
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.GET_PHONE_AUTH_CREDENTIAL_SUCCESSFULLY
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_PHONE_AUTH_CREDENTIAL_FAILED
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_PHONE_AUTH_CREDENTIAL_INVALID
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_PHONE_AUTH_CREDENTIAL_SUCCESSFULLY
import kr.co.soogong.master.ui.auth.signup.TimerTerm
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class Step2Fragment : BaseFragment<FragmentSignUpStep2Binding>(
    R.layout.fragment_sign_up_step2
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private val timer = object : CountDownTimer(LimitTime * 1000, TimerTerm) {
        override fun onTick(millisUntilFinished: Long) {
            binding.timerView.text =
                "${millisUntilFinished / 1000 / 60}:${millisUntilFinished / 1000 % 60}"
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
        registerEventObserve()

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
                    if (alertWrongCertificationCode.isVisible) alertWrongCertificationCode.isVisible =
                        false
                })

                if (!alertWrongCertificationCode.isVisible && !alertExpiredCertificationTime.isVisible && !alertInvalidCertificationCode.isVisible) {
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
        Timber.tag(TAG).d("initFirebaseAuthCallbacks: ")
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
        Timber.tag(TAG).d("startPhoneNumberVerification: ${viewModel.tel.value}")
        startTimer()
        viewModel.requestVerificationCode(
            callbackActivity = requireActivity(),
            callbacks = callbacks
        )
    }

    private fun verifyPhoneNumberWithCode() {
        Timber.tag(TAG)
            .d("verifyPhoneNumberWithCode: ${viewModel.storedVerificationId.value}, ${viewModel.certificationCode.value}")
        viewModel.getPhoneAuthCredential()
    }

    private fun resendVerificationCode() {
        Timber.tag(TAG)
            .d("resendVerificationCode: ${viewModel.tel.value}, ${viewModel.resendToken.value}")

        startTimer()
        viewModel.resendVerificationCode(
            callbackActivity = requireActivity(),
            callbacks = callbacks
        )
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                GET_PHONE_AUTH_CREDENTIAL_SUCCESSFULLY -> {
                    viewModel.signInWithPhoneAuthCredential()
                }
                SIGN_IN_PHONE_AUTH_CREDENTIAL_SUCCESSFULLY -> {
                    (activity as? SignUpActivity)?.moveToNext()
                }
                SIGN_IN_PHONE_AUTH_CREDENTIAL_INVALID -> {
                    binding.alertWrongCertificationCode.isVisible = true
                }
                SIGN_IN_PHONE_AUTH_CREDENTIAL_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "Step2Fragment"

        fun newInstance(): Step2Fragment {
            return Step2Fragment()
        }
    }
}