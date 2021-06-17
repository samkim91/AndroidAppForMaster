package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpPrivatePolicyBinding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_FAILED
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_SUCCESSFUL
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_UP_FAILED
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PrivatePolicyFragment : BaseFragment<FragmentSignUpPrivatePolicyBinding>(
    R.layout.fragment_sign_up_private_policy
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

            binding.agreedAll.setCheckClick {
                val status = agreedAll.checkBox.isChecked
                viewModel.agreedPrivacyPolicy.value = status
                viewModel.marketingPush.value = status
            }

            // TODO: 2021/06/17 이용약관 및 개인정보처리방침 버튼 및 화면 띄우기

            defaultButton.setOnClickListener {
                viewModel.agreedPrivacyPolicy.observe(viewLifecycleOwner, {
                    alertPrivacyPolicyAgreementRequired.isVisible = !it
                })

                if (!alertPrivacyPolicyAgreementRequired.isVisible) {
                    val dialog = CustomDialog(
                        DialogData.getAcceptNotificationDialogData(requireContext()),
                        yesClick = {
                            viewModel.appPush.value = true
                            viewModel.signUp()
                        },
                        noClick = {
                            viewModel.appPush.value = false
                            viewModel.signUp()
                        }
                    )

                    dialog.show(parentFragmentManager, dialog.tag)
                }
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.agreedPrivacyPolicy.observe(viewLifecycleOwner, {
            binding.agreedAll.checkBox.isChecked = it && viewModel.marketingPush.value!!
        })
        viewModel.marketingPush.observe(viewLifecycleOwner, {
            binding.agreedAll.checkBox.isChecked = it && viewModel.agreedPrivacyPolicy.value!!
        })
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            if (BuildConfig.DEBUG) {
                requireContext().toast(getString(R.string.sign_up_successfully))
                startActivity(MainActivityHelper.getIntent(requireContext()))
            } else {
                when (event) {
                    SIGN_IN_SUCCESSFUL -> {
                        requireContext().toast(getString(R.string.sign_up_successfully))
                        startActivity(MainActivityHelper.getIntent(requireContext()))
                    }
                    SIGN_UP_FAILED, SIGN_IN_FAILED -> {
                        requireContext().toast(getString(R.string.error_message_of_request_failed))
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "Step8Fragment"

        fun newInstance(): PrivatePolicyFragment {
            return PrivatePolicyFragment()
        }
    }
}