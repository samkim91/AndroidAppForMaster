package kr.co.soogong.master.presentation.ui.auth.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignInBinding
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.presentation.ui.auth.AuthContainerActivity
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.CREDENTIAL_CODE_REQUESTED
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.CREDENTIAL_CODE_REQUESTED_AGAIN
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.EXIST_USER
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.INVALID_CREDENTIAL
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.NOT_EXIST_USER
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.REQUIRED_TEL
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.TASK_FAILED
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.TASK_SUCCESSFUL
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.TOO_MANY_REQUEST
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel.Companion.TRY_AGAIN
import kr.co.soogong.master.presentation.ui.auth.signin.SignInViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.auth.signin.SignInViewModel.Companion.SIGN_IN_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.isValidPhoneNumber
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(
    R.layout.fragment_sign_in
) {
    private val viewModel: SignInViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserver()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            authVm = authViewModel
            lifecycleOwner = viewLifecycleOwner

            authPhoneNumberButtonTheme = ButtonTheme.Primary

            abHeader.setIvBackClickListener { (activity as AuthContainerActivity).onBackPressed() }

            initTimer()

            // 인증하기
            stibmtitAuthPhoneNumber.onButtonClick = View.OnClickListener {
                if (stibmtitAuthPhoneNumber.inputEnabled == false) {        // "재입력" 일 경우
                    stopAuth()
                    return@OnClickListener
                }

                authViewModel.tel.observe(viewLifecycleOwner) {
                    stibmtitAuthPhoneNumber.error =
                        if (!it.isValidPhoneNumber()) getString(R.string.invalid_phone_number) else null
                }

                if (stibmtitAuthPhoneNumber.error.isNullOrEmpty()) authViewModel.checkUserExist()
            }

            // 재요청하기
            tvResendCertificationCode.setOnClickListener { startAuth() }

            // 로그인
            bSignIn.setOnClickListener {
                authViewModel.certificationCode.observe(viewLifecycleOwner) {
                    stibmtitAuthPhoneNumber.textInputTimerError =
                        if (it.length != 6) getString(R.string.invalid_certification_code) else null
                }

                if (stibmtitAuthPhoneNumber.textInputTimerError.isNullOrEmpty()) authViewModel.makePhoneAuthCredential()
            }
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                SIGN_IN_SUCCESSFULLY -> startActivity(MainActivityHelper.getIntent(requireContext()))
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })

        with(binding) {
            authViewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
                when (action) {
                    REQUIRED_TEL -> stibmtitAuthPhoneNumber.error =
                        getString(R.string.invalid_phone_number)

                    EXIST_USER -> startAuth()
                    NOT_EXIST_USER -> binding.stibmtitAuthPhoneNumber.error =
                        getString(R.string.alert_unknown_user)

                    CREDENTIAL_CODE_REQUESTED -> requireContext().toast(getString(R.string.certification_code_requested))
                    CREDENTIAL_CODE_REQUESTED_AGAIN -> requireContext().toast(getString(R.string.certification_code_requested_again))

                    INVALID_CREDENTIAL -> {
                        stibmtitAuthPhoneNumber.textInputTimerError =
                            getString(R.string.invalid_credential)
                        stopAuth()
                    }
                    TOO_MANY_REQUEST -> {
                        requireContext().toast(getString(R.string.firebase_auth_too_many_requests))
                        stopAuth()
                    }
                    TRY_AGAIN -> {
                        requireContext().toast(getString(R.string.try_again))
                        stopAuth()
                    }

                    REQUEST_FAILED -> {
                        requireContext().toast(getString(R.string.error_message_of_request_failed))
                        stopAuth()
                    }
                }
            })
        }

        authViewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                TASK_SUCCESSFUL -> {
                    viewModel.requestSignIn(authViewModel.tel.value!!, value as String)
                }
                TASK_FAILED -> requireContext().toast(value as String)
            }
        })
    }

    private fun startAuth() {
        with(binding) {
            stibmtitAuthPhoneNumber.textInputTimer.startTimer {
                stibmtitAuthPhoneNumber.inputEnabled = false
                stibmtitAuthPhoneNumber.buttonText = getString(R.string.retyping)
                stibmtitAuthPhoneNumber.textInputTimerError = null
                authViewModel.startVerifyingPhoneNumber(requireActivity())
            }
        }
    }

    private fun stopAuth() {
        with(binding) {
            stibmtitAuthPhoneNumber.textInputTimer.stopTimer {
                stibmtitAuthPhoneNumber.inputEnabled = true
                stibmtitAuthPhoneNumber.buttonText = getString(R.string.certification)
                stibmtitAuthPhoneNumber.textInputButtonMedium.textInput.textInputEditText.setText("")
            }
        }
    }

    private fun initTimer() {
        binding.stibmtitAuthPhoneNumber.textInputTimer.initTimer(
            minute = 2,
            interval = 1L,
            onTick = { },
            onFinish = {
                binding.stibmtitAuthPhoneNumber.textInputTimerError =
                    getString(R.string.expired_certification_code)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.stibmtitAuthPhoneNumber.textInputTimer.stopTimer { }
    }

    companion object {
        private const val TAG = "SignInFragment"

        fun newInstance() = SignInFragment()
    }
}