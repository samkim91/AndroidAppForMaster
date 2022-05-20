package kr.co.soogong.master.presentation.ui.profile.detail.phonenumber

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPhoneNumberBinding
import kr.co.soogong.master.presentation.ui.auth.AuthViewModel
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_SUCCESS
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.EventObserver
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

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                authViewModel.verifyCertificationCode()
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
                AuthViewModel.REQUIRED_TEL -> binding.tilPhoneNumber.error =
                    getString(R.string.invalid_phone_number)

                AuthViewModel.REQUIRED_CODE ->
                    binding.tilCode.error = getString(R.string.invalid_certification_code)

                AuthViewModel.CLEAR_ERROR -> {
                    binding.tilPhoneNumber.error = null
                    binding.tilCode.error = null
                }

                AuthViewModel.CREDENTIAL_CODE_REQUESTED -> requireContext().toast(getString(R.string.certification_code_requested))

                AuthViewModel.TRY_AGAIN -> requireContext().toast(getString(R.string.try_again))
                AuthViewModel.VERIFIED_SUCCESSFUL -> viewModel.savePhoneNumber(authViewModel.tel.value!!)
                AuthViewModel.VERIFIED_FAILED -> requireContext().toast(getString(R.string.wrong_certification_code))

                BaseViewModel.REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
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

        authViewModel.certificationCode.observe(viewLifecycleOwner) {
            (activity as EditProfileContainerActivity).setSaveButtonEnabled(it.length > 5)
        }
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
        private val TAG = EditPhoneNumberFragment::class.java.simpleName

        fun newInstance() = EditPhoneNumberFragment()
    }
}