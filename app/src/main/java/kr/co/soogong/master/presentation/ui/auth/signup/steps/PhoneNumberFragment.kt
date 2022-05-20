package kr.co.soogong.master.presentation.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentPhoneNumberBinding
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_PHONE_NUMBER
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.utility.EventObserver
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
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")

        viewModel.message.observe(viewLifecycleOwner) { (key, _) ->
            when (key) {
                VALIDATE_PHONE_NUMBER -> authViewModel.verifyCertificationCode()
            }
        }

        authViewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                AuthViewModel.REQUIRED_TEL ->
                    binding.tilPhoneNumber.error = getString(R.string.invalid_phone_number)
                AuthViewModel.REQUIRED_CODE ->
                    binding.tilCode.error = getString(R.string.invalid_certification_code)

                AuthViewModel.CLEAR_ERROR -> {
                    binding.tilPhoneNumber.error = null
                    binding.tilCode.error = null
                }

                AuthViewModel.CREDENTIAL_CODE_REQUESTED -> requireContext().toast(getString(R.string.certification_code_requested))

                AuthViewModel.TRY_AGAIN -> requireContext().toast(getString(R.string.try_again))
                AuthViewModel.TASK_SUCCESSFUL -> {
                    viewModel.tel.value = authViewModel.tel.value
                    viewModel.setCurrentPage(1)
                }
                AuthViewModel.TASK_FAILED -> requireContext().toast(getString(R.string.wrong_certification_code))
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })

        authViewModel.event.observe(viewLifecycleOwner, EventObserver { (event, value) ->
            when (event) {
                AuthViewModel.EXIST_USER -> {
                    (value as Boolean).run {
                        if (this) showDialogForUserExist() else authViewModel.requestCertificationCode()
                    }
                }
            }
        })
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
        private val TAG = PhoneNumberFragment::class.java.simpleName

        fun newInstance() = PhoneNumberFragment()
    }
}