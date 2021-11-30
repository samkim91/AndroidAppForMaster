package kr.co.soogong.master.ui.profile.detail.warranty

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditWarrantyInformationBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.SAVE_MASTER_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditWarrantyInformationFragment : BaseFragment<FragmentEditWarrantyInformationBinding>(
    R.layout.fragment_edit_warranty_information
) {
    private val viewModel: EditWarrantyInformationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestWarrantyInformation()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            sdmWarrantyPeriod.adapter =
                ArrayAdapter(requireContext(),
                    R.layout.textview_item_dropdown,
                    viewModel.warrantyPeriods.map { it.first })

            sdmWarrantyPeriod.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                viewModel.selectedPeriod.value = viewModel.warrantyPeriods[position]
            }

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                viewModel.selectedPeriod.observe(viewLifecycleOwner, { pair ->
                    sdmWarrantyPeriod.error =
                        if (pair == null) getString(R.string.required_field_alert) else null
                })

                viewModel.warrantyDescription.observe(viewLifecycleOwner, {
                    stcWarrantyInformation.error =
                        if (it.length < 10) getString(R.string.fill_text_over_10) else null
                })

                if (viewModel.selectedPeriod.value?.second == -1) {     // 보증기간 없음 선택 시
                    if (sdmWarrantyPeriod.error.isNullOrBlank()) viewModel.saveWarrantyInfo()
                } else {        // 보증기간 있을 시
                    if (sdmWarrantyPeriod.error.isNullOrBlank() && stcWarrantyInformation.error.isNullOrBlank()) viewModel.saveWarrantyInfo()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_MASTER_SUCCESSFULLY -> activity?.onBackPressed()
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditWarrantyInformationFragment"

        fun newInstance() = EditWarrantyInformationFragment()
    }
}