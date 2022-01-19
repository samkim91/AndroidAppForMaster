package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentAgreementBinding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_UP_SUCCESSFULLY
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_AGREEMENT
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uihelper.auth.AgreementDetailActivityHelper
import kr.co.soogong.master.uihelper.auth.AgreementDetailActivityHelper.PRIVATE_POLICY
import kr.co.soogong.master.uihelper.auth.AgreementDetailActivityHelper.TERMS_OF_SERVICE
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.makeLinks
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class AgreementFragment : BaseFragment<FragmentAgreementBinding>(
    R.layout.fragment_agreement
) {
    private val viewModel: SignUpViewModel by viewModels({ requireParentFragment() })

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
            lifecycleOwner = viewLifecycleOwner

            cbAgreementAll.setOnClickListener {
                cbAgreementAll.isChecked.run {
                    viewModel.privacyPolicy.value = this
                    viewModel.marketingPush.value = this
                }
            }

            cbAgreementPrivacyPolicy.makeLinks(
                Pair("이용약관", View.OnClickListener {
                    startActivity(
                        AgreementDetailActivityHelper.getIntent(requireContext(), TERMS_OF_SERVICE)
                    )
                }),
                Pair("개인정보 처리방침", View.OnClickListener {
                    startActivity(
                        AgreementDetailActivityHelper.getIntent(requireContext(), PRIVATE_POLICY)
                    )
                })
            )
        }
    }

    private fun registerEventObserver() {
        viewModel.validation.observe(viewLifecycleOwner, { validation ->
            if (validation == VALIDATE_AGREEMENT) {
                viewModel.privacyPolicy.observe(viewLifecycleOwner, {
                    binding.tvAlert.isVisible = !it
                })

                if (!binding.tvAlert.isVisible) viewModel.signUp()
            }
        })

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SIGN_UP_SUCCESSFULLY -> {
                    requireContext().toast(getString(R.string.sign_up_successfully))
                    startActivity(MainActivityHelper.getIntent(requireContext()))
                }
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "AgreementFragment"

        fun newInstance() = AgreementFragment()
    }
}