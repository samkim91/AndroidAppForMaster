package kr.co.soogong.master.ui.profile.detail.requiredinformation.phonenumber

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPhoneNumberBinding
import kr.co.soogong.master.ui.auth.signup.LimitTime
import kr.co.soogong.master.ui.auth.signup.TimerTerm
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.detail.requiredinformation.phonenumber.EditPhoneNumberViewModel.Companion.PHONE_NUMBER_EXIST
import kr.co.soogong.master.ui.profile.detail.requiredinformation.phonenumber.EditPhoneNumberViewModel.Companion.PHONE_NUMBER_NOT_EXIST
import kr.co.soogong.master.ui.profile.detail.requiredinformation.phonenumber.EditPhoneNumberViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.phonenumber.EditPhoneNumberViewModel.Companion.SAVE_PHONE_NUMBER_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.PhoneNumberHelper
import kr.co.soogong.master.utility.extension.toast
import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class EditPhoneNumberFragment :
    BaseFragment<FragmentEditPhoneNumberBinding>(
        R.layout.fragment_edit_phone_number
    ) {
    private val viewModel: EditPhoneNumberViewModel by viewModels()

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private val timer = object : CountDownTimer(LimitTime * 1000, TimerTerm) {
        override fun onTick(millisUntilFinished: Long) {
            binding.timerView.text =
                "${millisUntilFinished / 1000 / 60}:${millisUntilFinished / 1000 % 60}"
        }

        override fun onFinish() {
            binding.defaultButton.isEnabled = false
            binding.alertExpiredCertificationTime.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()

        initFirebaseAuthCallbacks()
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestProfile()
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

            id.setButtonClickListener {
                if (viewModel.isEnabled.value!!) {
                    viewModel.changeEnabled()
                    return@setButtonClickListener
                }

                viewModel.tel.observe(viewLifecycleOwner, {
                    id.alertVisible = !ValidationHelper.isPhoneNumberFormat(it)
                })

                if (!id.alertVisible) viewModel.checkUserExist()
            }

            requestCertificationCodeAgainText.setOnClickListener {
                resendVerificationCode()
            }

            defaultButton.setOnClickListener {
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

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        bind {
            viewModel.isEnabled.observe(viewLifecycleOwner, { isEnabled ->
                id.setFirstEditTextEnable = !isEnabled
                id.buttonColor = isEnabled
                id.buttonBackground = isEnabled
                id.buttonText =
                    if (!isEnabled) getString(R.string.certification_text) else getString(R.string.retype_text)
                certificationCodeContainer.isVisible = isEnabled
                requestCertificationCodeAgainGroup.isVisible = isEnabled
            })
        }

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_PHONE_NUMBER_SUCCESSFULLY -> activity?.onBackPressed()
                PHONE_NUMBER_EXIST -> showDialog()
                PHONE_NUMBER_NOT_EXIST -> startPhoneNumberVerification()
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    private fun initFirebaseAuthCallbacks() {
        Timber.tag(TAG).d("initFirebaseAuthCallbacks: ")
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Timber.tag(TAG).d("onVerificationCompleted: $credential")
                viewModel.phoneAuthCredential.value = credential
                signInWithPhoneAuthCredential()
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
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                Timber.tag(TAG).d("onCodeSent: $verificationId, $token")
                binding.defaultButton.isEnabled = true

                viewModel.storedVerificationId.value = verificationId
                viewModel.resendToken.value = token
            }
        }
    }

    private fun startPhoneNumberVerification() {
        Timber.tag(TAG).d("startPhoneNumberVerification: ${viewModel.tel.value}")
        viewModel.changeEnabled()
        startTimer()

        viewModel.auth.value?.let { auth ->
            viewModel.tel.value?.let { phoneNumber ->
                requireContext().toast(getString(R.string.certification_code_requested))

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(PhoneNumberHelper.toGlobalNumber(phoneNumber))      // Phone number to verify
                    .setTimeout(
                        LimitTime,
                        TimeUnit.SECONDS
                    )                            // Timeout and unit
                    .setActivity(requireActivity())                                      // Activity (for callback binding)
                    .setCallbacks(callbacks)                                            // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }
    }

    private fun verifyPhoneNumberWithCode() {
        Timber.tag(TAG)
            .d("verifyPhoneNumberWithCode: ${viewModel.storedVerificationId.value}, ${viewModel.certificationCode.value}")

        if (!viewModel.storedVerificationId.value.isNullOrEmpty() && !viewModel.certificationCode.value.isNullOrEmpty()) {
            viewModel.phoneAuthCredential.value =
                PhoneAuthProvider.getCredential(
                    viewModel.storedVerificationId.value!!,
                    viewModel.certificationCode.value!!
                )
            Timber.tag(TAG).d("getPhoneAuthCredential: ${viewModel.phoneAuthCredential.value}")
            signInWithPhoneAuthCredential()
        }
    }

    private fun signInWithPhoneAuthCredential() {
        Timber.tag(TAG)
            .d("signInWithPhoneAuthCredential: ${viewModel.phoneAuthCredential.value} / ${viewModel.auth.value}")

        viewModel.phoneAuthCredential.value?.let { credential ->
            viewModel.auth.value?.signInWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.tag(TAG).d("signInWithPhoneAuthCredential successfully: ")
                    viewModel.uid.value = task.result?.user?.uid
                    viewModel.savePhoneNumber()
                } else {
                    // Sign in failed, display a message and update the UI
                    Timber.tag(TAG).d("signInWithPhoneAuthCredential failed: ${task.exception}")
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

    private fun resendVerificationCode() {
        Timber.tag(TAG)
            .d("resendVerificationCode: ${viewModel.tel.value}, ${viewModel.resendToken.value}")
        startTimer()
        requireContext().toast(getString(R.string.certification_code_requested_again))

        viewModel.auth.value?.let { auth ->
            viewModel.tel.value?.let { phoneNumber ->
                val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(PhoneNumberHelper.toGlobalNumber(phoneNumber))
                    .setTimeout(LimitTime, TimeUnit.SECONDS)
                    .setActivity(requireActivity())
                    .setCallbacks(callbacks)
                viewModel.resendToken.value?.let { resendToken ->
                    optionsBuilder.setForceResendingToken(resendToken)          // callback's ForceResendingToken
                }
                PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
            }
        }
    }

    private fun startTimer() {
        binding.defaultButton.isEnabled = false
        binding.alertExpiredCertificationTime.visibility = View.GONE
        timer.start()
    }

    private fun stopTimer() {
        timer.cancel()
    }

    private fun showDialog() {
        CustomDialog.newInstance(DialogData.getUserExistDialogData(requireContext()))
            .let {
                it.setButtonsClickListener(
                    onPositive = {},
                    onNegative = {}
                )
                it.show(parentFragmentManager, it.tag)
            }
    }

    companion object {
        private const val TAG = "EditPhoneNumberViewModel"

        fun newInstance() = EditPhoneNumberFragment()
    }
}