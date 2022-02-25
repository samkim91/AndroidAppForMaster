package kr.co.soogong.master.presentation.ui.profile.detail.phonenumber

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPhoneNumberBinding
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_SUCCESS
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.isValidPhoneNumber
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditPhoneNumberFragment : BaseFragment<FragmentEditPhoneNumberBinding>(
    R.layout.fragment_edit_phone_number
) {

    private val editProfileContainerViewModel: EditProfileContainerViewModel by activityViewModels()
    private val viewModel: EditPhoneNumberViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
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
                if (stibmtitAuthPhoneNumber.inputEnabled == false) {     // "재입력" 일 경우
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

            // 저장하기
            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                authViewModel.certificationCode.observe(viewLifecycleOwner) {
                    stibmtitAuthPhoneNumber.textInputTimerError =
                        if (it.length != 6) getString(R.string.invalid_certification_code) else null
                }

                if (stibmtitAuthPhoneNumber.textInputTimerError.isNullOrEmpty()) authViewModel.makePhoneAuthCredential()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                REQUEST_SUCCESS -> editProfileContainerViewModel.setAction(REQUEST_SUCCESS)
            }
        })

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

                BaseViewModel.REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                    stopAuth()
                }
            }
        })

        authViewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                AuthViewModel.TASK_SUCCESSFUL -> {
                    viewModel.savePhoneNumber(authViewModel.tel.value!!)
                }
                AuthViewModel.TASK_FAILED -> requireContext().toast(value as String)
            }
        })

        authViewModel.certificationCode.observe(viewLifecycleOwner) {
            (activity as EditProfileContainerActivity).setSaveButtonEnabled(it.length > 5)
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
        DefaultDialog.newInstance(DialogData.getUserExist())
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