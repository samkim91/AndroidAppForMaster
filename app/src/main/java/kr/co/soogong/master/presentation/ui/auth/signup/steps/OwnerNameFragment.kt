package kr.co.soogong.master.presentation.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentOwnerNameBinding
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.SIGN_UP_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_OWNER_NAME
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.uihelper.auth.AgreementDetailActivityHelper
import kr.co.soogong.master.presentation.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.extension.makeLinks
import timber.log.Timber

@AndroidEntryPoint
class OwnerNameFragment : BaseFragment<FragmentOwnerNameBinding>(
    R.layout.fragment_owner_name
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
        }

        setTextClickable()
    }

    private fun setTextClickable() {
        with(binding) {
            cbAgreementTermsOfService.makeLinks(
                Pair(getString(R.string.terms_of_service), View.OnClickListener {
                    startActivity(
                        AgreementDetailActivityHelper.getIntent(requireContext(),
                            AgreementDetailActivityHelper.TERMS_OF_SERVICE)
                    )
                })
            )

            cbAgreementPrivacyPolicy.makeLinks(
                Pair(getString(R.string.private_policy), View.OnClickListener {
                    startActivity(
                        AgreementDetailActivityHelper.getIntent(requireContext(),
                            AgreementDetailActivityHelper.PRIVATE_POLICY)
                    )
                })
            )

            cbRepairInPerson.makeLinks(
                Pair(getString(R.string.repair_in_person), View.OnClickListener {
                    DefaultDialog.newInstance(DialogData.getRepairInPersonIntroduction())
                        .let {
                            it.setButtonsClickListener(
                                onPositive = { },
                                onNegative = { }
                            )
                            it.show(parentFragmentManager, it.tag)
                        }
                })
            )
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")
        viewModel.message.observe(viewLifecycleOwner) { (key, value) ->
            when (key) {
                SIGN_UP_SUCCESSFULLY -> startActivity(MainActivityHelper.getIntent(requireContext()))
            }
        }

        viewModel.validation.observe(viewLifecycleOwner) { validation ->
            when (validation) {
                VALIDATE_OWNER_NAME -> validateValues()
            }
        }

        viewModel.ownerName.observe(viewLifecycleOwner, { name ->
            binding.groupAgreement.isVisible = name.isNotEmpty()
            binding.tvAlert.isVisible = false
        })
    }

    private fun validateValues() {
        Timber.tag(TAG).d("validateValues: ")

        viewModel.ownerName.observe(viewLifecycleOwner, {
            binding.stiOwnerName.error =
                if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
        })

        viewModel.termsOfService.observe(viewLifecycleOwner) {
            binding.tvAlert.isVisible =
                (!it || viewModel.privacyPolicy.value == false || viewModel.repairInPerson.value == false) && binding.groupAgreement.isVisible
        }

        viewModel.privacyPolicy.observe(viewLifecycleOwner) {
            binding.tvAlert.isVisible =
                (!it || viewModel.termsOfService.value == false || viewModel.repairInPerson.value == false) && binding.groupAgreement.isVisible
        }

        viewModel.repairInPerson.observe(viewLifecycleOwner) {
            binding.tvAlert.isVisible =
                (!it || viewModel.privacyPolicy.value == false || viewModel.termsOfService.value == false) && binding.groupAgreement.isVisible
        }

        if (binding.stiOwnerName.error.isNullOrEmpty() && !binding.tvAlert.isVisible) viewModel.signUp()
    }


    companion object {
        private const val TAG = "OwnerNameFragment"

        fun newInstance() = OwnerNameFragment()
    }
}