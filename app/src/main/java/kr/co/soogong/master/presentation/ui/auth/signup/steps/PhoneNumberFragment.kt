package kr.co.soogong.master.presentation.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.databinding.FragmentPhoneNumberBinding
import kr.co.soogong.master.presentation.LIMIT_TIME_TO_AUTH
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.PHONE_NUMBER_EXIST
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.PHONE_NUMBER_NOT_EXIST
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_PHONE_NUMBER
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.PhoneNumberHelper
import kr.co.soogong.master.utility.extension.isValidPhoneNumber
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class PhoneNumberFragment : BaseFragment<FragmentPhoneNumberBinding>(
    R.layout.fragment_phone_number
) {
    private val viewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserver()
        initFirebaseAuthCallbacks()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            authPhoneNumberButtonTheme = ButtonTheme.Primary

            stibmtitAuthPhoneNumber.textInputTimer.initTimer(
                minute = 2,
                interval = 1L,
                onTick = { },
                onFinish = {
                    binding.stibmtitAuthPhoneNumber.textInputTimerError =
                        getString(R.string.expired_certification_code)
                }
            )

            // 휴대폰 번호 validation 및 존재하는 유저인지 확인
            stibmtitAuthPhoneNumber.onButtonClick = View.OnClickListener {
                if (stibmtitAuthPhoneNumber.inputEnabled == false) {     // "재입력"에 대한 코드
                    stibmtitAuthPhoneNumber.inputEnabled = true
                    stibmtitAuthPhoneNumber.buttonText = getString(R.string.certify)
                    stibmtitAuthPhoneNumber.textInputButtonMedium.textInput.textInputEditText.setText(
                        "")
                    return@OnClickListener
                }

                // "인증하기"에 대한 코드
                viewModel.tel.observe(viewLifecycleOwner, {
                    stibmtitAuthPhoneNumber.error =
                        if (!it.isValidPhoneNumber()) getString(R.string.invalid_phone_number) else null
                })

                if (stibmtitAuthPhoneNumber.error.isNullOrEmpty()) viewModel.checkUserExist()
            }

            // "재요청하기"에 대한 코드
            tvResendCertificationCode.setOnClickListener {
                startPhoneNumberVerification(isFirst = false)
            }
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                PHONE_NUMBER_EXIST -> showDialogForUserExist()
                PHONE_NUMBER_NOT_EXIST -> startPhoneNumberVerification(isFirst = true)
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })

        viewModel.validation.observe(viewLifecycleOwner, { validation ->
            if (validation == VALIDATE_PHONE_NUMBER) {
                viewModel.certificationCode.observe(viewLifecycleOwner, {
                    binding.stibmtitAuthPhoneNumber.textInputTimerError =
                        if (it.length != 6) getString(R.string.invalid_certification_code) else null
                })

                if (binding.stibmtitAuthPhoneNumber.textInputTimerError.isNullOrEmpty()) verifyPhoneNumberWithCode()
            }
        })
    }

    private fun initFirebaseAuthCallbacks() {
        Timber.tag(TAG).d("initFirebaseAuthCallbacks: ")
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Timber.tag(TAG).d("onVerificationCompleted: $credential")
                viewModel.phoneAuthCredential.value = credential
                moveToNextWithPhoneAuthCredential()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Timber.tag(TAG).d("onVerificationFailed: $e")
                binding.stibmtitAuthPhoneNumber.textInputTimer.stopTimer { }

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    binding.stibmtitAuthPhoneNumber.textInputTimerError =
                        getString(R.string.invalid_credential)
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
                viewModel.storedVerificationId.value = verificationId
                viewModel.resendToken.value = token
            }
        }
    }

    private fun startPhoneNumberVerification(isFirst: Boolean) {
        Timber.tag(TAG)
            .d("startPhoneNumberVerification: ${viewModel.tel.value}")
        with(binding) {
            stibmtitAuthPhoneNumber.inputEnabled = false
            stibmtitAuthPhoneNumber.buttonText = getString(R.string.retyping)
            stibmtitAuthPhoneNumber.textInputTimer.startTimer {
                stibmtitAuthPhoneNumber.textInputTimerError = null
            }
        }

        viewModel.auth.value?.let { auth ->
            viewModel.tel.value?.let { phoneNumber ->
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(PhoneNumberHelper.toGlobalNumber(phoneNumber))      // Phone number to verify
                    .setTimeout(
                        LIMIT_TIME_TO_AUTH,
                        TimeUnit.SECONDS
                    )                            // Timeout and unit
                    .setActivity(requireActivity())                                      // Activity (for callback binding)
                    .setCallbacks(callbacks)                                            // OnVerificationStateChangedCallbacks

                // 재요청일 땐, resendToken 을 강제로 set 해준다
                if (!isFirst) viewModel.resendToken.value?.let { resendToken ->
                    Timber.tag(TAG).d("resendVerificationCode: $resendToken")
                    options.setForceResendingToken(resendToken)          // callback's ForceResendingToken
                }

                PhoneAuthProvider.verifyPhoneNumber(options.build())

                requireContext().toast(
                    if (isFirst) getString(R.string.certification_code_requested)
                    else getString(R.string.certification_code_requested_again)
                )
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
            moveToNextWithPhoneAuthCredential()
        }
    }

    private fun moveToNextWithPhoneAuthCredential() {
        Timber.tag(TAG)
            .d("moveToNextWithPhoneAuthCredential: ${viewModel.phoneAuthCredential.value} / ${viewModel.auth.value}")

        viewModel.phoneAuthCredential.value?.let { credential ->
            viewModel.auth.value?.signInWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.tag(TAG)
                        .d("signInWithPhoneAuthCredential successfully: ")
                    viewModel.uid.value = task.result?.user?.uid
                    viewModel.moveToNext()
                } else {
                    // Sign in failed, display a message and update the UI
                    Timber.tag(TAG)
                        .d("signInWithPhoneAuthCredential failed: ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        binding.stibmtitAuthPhoneNumber.textInputTimerError =
                            getString(R.string.wrong_certification_code)
                    } else {
                        requireContext().toast(getString(R.string.error_message_of_request_failed))
                    }

                    binding.stibmtitAuthPhoneNumber.textInputTimer.stopTimer { }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.stibmtitAuthPhoneNumber.textInputTimer.stopTimer { }
    }

    private fun showDialogForUserExist() {
        DefaultDialog.newInstance(DialogData.getExistentUser(), false)
            .let {
                it.setButtonsClickListener(
                    onPositive = {      // 초기 화면으로 이동.. todo 바로 로그인화면으로 이동하면 더 좋을 듯 싶다.
                        activity?.supportFragmentManager?.popBackStack()
                    },
                    onNegative = {}
                )
                it.show(parentFragmentManager, it.tag)
            }
    }

    companion object {
        private const val TAG = "PhoneNumberFragment"

        fun newInstance() = PhoneNumberFragment()
    }
}