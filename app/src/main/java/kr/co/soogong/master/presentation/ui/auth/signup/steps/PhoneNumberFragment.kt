package kr.co.soogong.master.presentation.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentPhoneNumberBinding
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_PHONE_NUMBER
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.isValidPhoneNumber
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PhoneNumberFragment : BaseFragment<FragmentPhoneNumberBinding>(
    R.layout.fragment_phone_number
) {
    private val viewModel: SignUpViewModel by viewModels({ requireParentFragment() })
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

            initTimer()

            // 인증하기
            stibmtitAuthPhoneNumber.onButtonClick = View.OnClickListener {
                if (stibmtitAuthPhoneNumber.inputEnabled == false) {        // "재입력" 일 경우
                    stibmtitAuthPhoneNumber.textInputButtonMedium.textInput.textInputEditText.setText(
                        "")
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
            tvResendCertificationCode.setOnClickListener {
                startAuth()
                authViewModel.startVerifyingPhoneNumber(requireActivity())
            }
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")

        viewModel.message.observe(viewLifecycleOwner) { (key, _) ->
            when (key) {
                VALIDATE_PHONE_NUMBER -> validateValues()
            }
        }

        authViewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                AuthViewModel.REQUIRED_TEL -> binding.stibmtitAuthPhoneNumber.error =
                    getString(R.string.invalid_phone_number)

                AuthViewModel.EXIST_USER -> showDialogForUserExist()

                AuthViewModel.NOT_EXIST_USER -> {
                    startAuth()
                    authViewModel.startVerifyingPhoneNumber(requireActivity())
                }

                AuthViewModel.CREDENTIAL_CODE_REQUESTED -> requireContext().toast(getString(R.string.certification_code_requested))
                AuthViewModel.CREDENTIAL_CODE_REQUESTED_AGAIN -> requireContext().toast(getString(R.string.certification_code_requested_again))

                AuthViewModel.INVALID_CREDENTIAL -> {
                    binding.stibmtitAuthPhoneNumber.textInputTimerError =
                        getString(R.string.invalid_credential)
                    stopAuth()
                }
                AuthViewModel.TOO_MANY_REQUEST -> {
                    requireContext().toast(getString(R.string.firebase_auth_too_many_requests))
                    stopAuth()
                }
                AuthViewModel.TRY_AGAIN -> {
                    requireContext().toast(getString(R.string.try_again))
                    stopAuth()
                }

                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                    stopAuth()
                }
            }
        })

        authViewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                AuthViewModel.TASK_SUCCESSFUL -> {
                    viewModel.uid.value = value as String
                    viewModel.tel.value = authViewModel.tel.value
                    viewModel.setCurrentPage(1)
                }
                AuthViewModel.TASK_FAILED -> requireContext().toast(value as String)
            }
        })
    }

    private fun validateValues() {
        Timber.tag(TAG).d("validateValues: ")

        authViewModel.certificationCode.observe(viewLifecycleOwner) {
            binding.stibmtitAuthPhoneNumber.textInputTimerError =
                if (it.length != 6) getString(R.string.invalid_certification_code) else null
        }

        if (binding.stibmtitAuthPhoneNumber.textInputTimerError.isNullOrEmpty()) authViewModel.makePhoneAuthCredential()
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

    private fun startAuth() {
        with(binding) {
            stibmtitAuthPhoneNumber.textInputTimer.startTimer {
                stibmtitAuthPhoneNumber.inputEnabled = false
                stibmtitAuthPhoneNumber.buttonText = getString(R.string.retyping)
                stibmtitAuthPhoneNumber.textInputTimerError = null
            }
        }
    }

    private fun stopAuth() {
        with(binding) {
            stibmtitAuthPhoneNumber.textInputTimer.stopTimer {
                stibmtitAuthPhoneNumber.inputEnabled = true
                stibmtitAuthPhoneNumber.buttonText = getString(R.string.certification)
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