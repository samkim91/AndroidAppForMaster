package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpPrivatePolicyBinding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_UP_FAILED
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_UP_SUCCESSFULLY
import kr.co.soogong.master.ui.base.BaseFragment
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
                viewModel.privacyPolicy.value = status
                viewModel.marketingPush.value = status
            }

            // TODO: 2021/06/17 이용약관 및 개인정보처리방침 버튼 및 화면 띄우기

            defaultButton.setOnClickListener {
                viewModel.privacyPolicy.observe(viewLifecycleOwner, {
                    alertPrivacyPolicyAgreementRequired.isVisible = !it
                })

                if (!alertPrivacyPolicyAgreementRequired.isVisible) {
                    viewModel.signUp()
                }
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.privacyPolicy.observe(viewLifecycleOwner, {
            binding.agreedAll.checkBox.isChecked = it && viewModel.marketingPush.value!!
        })
        viewModel.marketingPush.observe(viewLifecycleOwner, {
            binding.agreedAll.checkBox.isChecked = it && viewModel.privacyPolicy.value!!
        })
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SIGN_UP_SUCCESSFULLY -> {
                    requireContext().toast(getString(R.string.sign_up_successfully))
                    startActivity(MainActivityHelper.getIntent(requireContext()))
                }
                SIGN_UP_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "PrivatePolicyFragment"

        fun newInstance(): PrivatePolicyFragment {
            return PrivatePolicyFragment()
        }
    }
}