package kr.co.soogong.master.presentation.ui.profile.detail.masterconfig

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditMasterConfigBinding
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.presentation.atomic.molecules.SubheadlineChipGroup
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_SUCCESS
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class EditMasterConfigFragment : BaseFragment<FragmentEditMasterConfigBinding>(
    R.layout.fragment_edit_master_config
) {

    private val viewModel: EditMasterConfigViewModel by viewModels()
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

            initChipsGroup()
            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                getCheckedChips()
                viewModel.saveFlexibleCosts()
            }
        }
    }

    private fun initChipsGroup() {
        SubheadlineChipGroup.initChoiceRectangularChips(
            requireContext(),
            layoutInflater,
            binding.scgTravelCost,
            viewModel.travelCosts.map { it.inKorean }
        )

        SubheadlineChipGroup.initChoiceRectangularChips(
            requireContext(),
            layoutInflater,
            binding.scgCraneUsage,
            viewModel.craneUsages.map { it.inKorean }
        )

        SubheadlineChipGroup.initChoiceRectangularChips(
            requireContext(),
            layoutInflater,
            binding.scgPackageCost,
            viewModel.packageCosts.map { it.inKorean }
        )

        SubheadlineChipGroup.initChoiceRectangularChips(
            requireContext(),
            layoutInflater,
            binding.scgOtherOptions,
            viewModel.otherOptions.map { it.inKorean }
        )
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.otherOption.observe(viewLifecycleOwner) { codeTableList ->
            Timber.tag(TAG).w("otherOption: $codeTableList")
            codeTableList.map { codeTable ->
                binding.scgOtherOptions.container.children.find { chip ->
                    (chip as Chip).text.toString() == codeTable.inKorean
                }?.run {
                    (this as Chip).isChecked = true
                }
            }
        }

        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                REQUEST_SUCCESS -> editProfileContainerViewModel.setAction(REQUEST_SUCCESS)
            }
        })
    }

    private fun getCheckedChips() {
        viewModel.otherOption.clear()
        viewModel.otherOption.addAll(
            binding.scgOtherOptions.container.checkedChipIds.map { chipId ->
                binding.scgOtherOptions.container.findViewById<Chip>(chipId).run {
                    CodeTable.getCodeTableByKorean(this.text.toString())
                }!!
            })
    }

    companion object {
        private const val TAG = "EditFlexibleCostFragment"

        fun newInstance() = EditMasterConfigFragment()
    }
}