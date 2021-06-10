package kr.co.soogong.master.ui.profile.edit.requiredinformation.businesstypes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.BusinessType
import kr.co.soogong.master.databinding.FragmentEditBusinessTypesBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businesstypes.EditBusinessTypesViewModel.Companion.GET_BUSINESS_TYPES_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businesstypes.EditBusinessTypesViewModel.Companion.SAVE_BUSINESS_TYPES_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businesstypes.EditBusinessTypesViewModel.Companion.SAVE_BUSINESS_TYPES_SUCCESSFULLY
import kr.co.soogong.master.utility.BusinessTypeChipGroupHelper
import kr.co.soogong.master.uihelper.major.MajorActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditBusinessTypesFragment : BaseFragment<FragmentEditBusinessTypesBinding>(
    R.layout.fragment_edit_business_types
) {
    private val viewModel: EditBusinessTypesViewModel by viewModels()

    private var getBusinessTypeLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedBusinessType: BusinessType by lazy {
                    data?.getParcelableExtra(MajorActivityHelper.BUNDLE_BUSINESS_TYPE)
                        ?: BusinessType(null, null)
                }
                BusinessTypeChipGroupHelper.makeEntryChipGroupWithSubtitleForBusinessTypes(
                    layoutInflater = layoutInflater,
                    container = binding.businessTypeContainer,
                    newBusinessType = selectedBusinessType,
                    viewModelBusinessTypes = viewModel.businessTypes
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestBusinessTypes()
        BusinessTypeChipGroupHelper.addBusinessTypesToContainer(
            layoutInflater = layoutInflater,
            container = binding.businessTypeContainer,
            viewModelBusinessTypes = viewModel.businessTypes
        )
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            businessType.setButtonClickListener {
                getBusinessTypeLauncher.launch(
                    Intent(
                        MajorActivityHelper.getIntent(
                            requireContext()
                        )
                    )
                )
            }

            defaultButton.setOnClickListener {
                viewModel.businessTypes.observe(viewLifecycleOwner, {
                    businessType.alertVisible = it.isNullOrEmpty()
                })

                if (!businessType.alertVisible) viewModel.saveBusinessTypes()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_BUSINESS_TYPES_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_BUSINESS_TYPES_FAILED, GET_BUSINESS_TYPES_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditBusinessTypesFragment"

        fun newInstance() = EditBusinessTypesFragment()
    }
}