package kr.co.soogong.master.presentation.ui.profile.detail.warranty

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditWarrantyInformationBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_SUCCESS
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class EditWarrantyInformationFragment : BaseFragment<FragmentEditWarrantyInformationBinding>(
    R.layout.fragment_edit_warranty_information
) {

    private val viewModel: EditWarrantyInformationViewModel by viewModels()
    private val editProfileContainerViewModel: EditProfileContainerViewModel by activityViewModels()

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

            sdmWarrantyPeriod.dropdownAdapter = ArrayAdapter(requireContext(),
                R.layout.textview_item_dropdown,
                viewModel.warrantyPeriods.map { it.first })

            sdmWarrantyPeriod.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                viewModel.selectedPeriod.value = viewModel.warrantyPeriods[position]
            }

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                viewModel.selectedPeriod.observe(viewLifecycleOwner) { pair ->
                    sdmWarrantyPeriod.dropdownError =
                        if (pair == null) getString(R.string.required_field_alert) else null
                }

                viewModel.warrantyDescription.observe(viewLifecycleOwner) {
                    stcWarrantyInformation.error =
                        if (it.length < 10) getString(R.string.fill_text_over_10) else null
                }

                if (viewModel.selectedPeriod.value?.second == -1) {     // 보증기간 없음 선택 시
                    if (sdmWarrantyPeriod.dropdownError.isNullOrEmpty()) viewModel.saveWarrantyInfo()
                } else {        // 보증기간 있을 시
                    if (sdmWarrantyPeriod.dropdownError.isNullOrEmpty() && stcWarrantyInformation.error.isNullOrEmpty()) viewModel.saveWarrantyInfo()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                REQUEST_SUCCESS -> editProfileContainerViewModel.setAction(REQUEST_SUCCESS)
            }
        })
    }

    companion object {
        private const val TAG = "EditWarrantyInformationFragment"

        fun newInstance() = EditWarrantyInformationFragment()
    }
}