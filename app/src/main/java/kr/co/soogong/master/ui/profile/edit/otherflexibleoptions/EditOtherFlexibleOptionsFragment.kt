package kr.co.soogong.master.ui.profile.edit.otherflexibleoptions

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditFlexibleCostBinding
import kr.co.soogong.master.databinding.FragmentEditOtherFlexibleOptionsBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.flexiblecost.FlexibleCostChipGroupHelper.CRANE_USAGE
import kr.co.soogong.master.ui.profile.edit.flexiblecost.FlexibleCostChipGroupHelper.PACKAGE_COST
import kr.co.soogong.master.ui.profile.edit.flexiblecost.FlexibleCostChipGroupHelper.TRAVEL_COST
import timber.log.Timber

@AndroidEntryPoint
class EditOtherFlexibleOptionsFragment : BaseFragment<FragmentEditOtherFlexibleOptionsBinding>(
    R.layout.fragment_edit_other_flexible_options
) {
    private val viewModel: EditOtherFlexibleOptionsViewModel by viewModels()

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

            initChips()
            defaultButton.setOnClickListener {
                saveCheckedChips()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    private fun initChips() {
        OtherFlexibleOptionsChipGroupHelper(layoutInflater, binding.otherOptionsChipGroup)
        viewModel.getFlexibleCosts()
        // 가져온 text들을 chip group에서 찾아서 selected 표시를 해준다.
        if (viewModel.otherFlexibleOptions.getItemCount() > 0) {
            viewModel.otherFlexibleOptions.value?.map { item ->
                binding.otherOptionsChipGroup.chipGroup.children.forEach {
                    val chip = it as? Chip
                    if (chip?.text.toString() == item) chip?.isChecked = true
                }
            }
        }
    }

    private fun saveCheckedChips() {
        viewModel.otherFlexibleOptions.clear()
        val checkChipIds: List<Int> = binding.otherOptionsChipGroup.chipGroup.checkedChipIds
        checkChipIds.forEach { chipId ->
            val chipText = binding.otherOptionsChipGroup.chipGroup.findViewById<Chip>(chipId)?.text
            chipText?.let {
                viewModel.otherFlexibleOptions.add(it.toString())
            }
        }
        viewModel.saveFlexibleCosts()
    }

    companion object {
        private const val TAG = "EditOtherFlexibleOptionsFragment"

        fun newInstance() = EditOtherFlexibleOptionsFragment()
    }
}