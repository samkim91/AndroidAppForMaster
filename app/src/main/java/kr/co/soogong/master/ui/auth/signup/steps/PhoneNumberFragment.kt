package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpPhoneNumberBinding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.CHECK_PHONE_NUMBER_FAILED
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.PHONE_NUMBER_EXIST
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.PHONE_NUMBER_NOT_EXIST
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PhoneNumberFragment : BaseFragment<FragmentSignUpPhoneNumberBinding>(
    R.layout.fragment_sign_up_phone_number
) {
    private val viewModel: SignUpViewModel by activityViewModels()

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
            lifecycleOwner = viewLifecycleOwner

            defaultButton.setOnClickListener {
                viewModel.tel.observe(viewLifecycleOwner, {
                    phoneNumber.alertVisible = it.isNullOrEmpty() || it.length < 10
                })

                if (!phoneNumber.alertVisible) {
                    viewModel.checkUserExist()
                }
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                PHONE_NUMBER_EXIST -> {
                    showDialog()
                }
                PHONE_NUMBER_NOT_EXIST -> {
                    (activity as? SignUpActivity)?.moveToNext()
                }
                CHECK_PHONE_NUMBER_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun showDialog() {
        CustomDialog.newInstance(DialogData.getExistentUserDialogData(requireContext()))
            .let {
                it.setButtonsClickListener(
                    onPositive = { (activity as? SignUpActivity)?.finishActivityWithoutDialog() },
                    onNegative = { }
                )
                it.show(parentFragmentManager, it.tag)
            }
    }

    companion object {
        private const val TAG = "PhoneNumberFragment"

        fun newInstance(): PhoneNumberFragment {
            return PhoneNumberFragment()
        }
    }
}