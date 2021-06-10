package kr.co.soogong.master.ui.profile.edit.otherflexibleoptions

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditOtherFlexibleOptionsBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.otherflexibleoptions.EditOtherFlexibleOptionsViewModel.Companion.GET_OTHER_FLEXIBLE_OPTIONS_FAILED
import kr.co.soogong.master.ui.profile.edit.otherflexibleoptions.EditOtherFlexibleOptionsViewModel.Companion.SAVE_OTHER_FLEXIBLE_OPTIONS_FAILED
import kr.co.soogong.master.ui.profile.edit.otherflexibleoptions.EditOtherFlexibleOptionsViewModel.Companion.SAVE_OTHER_FLEXIBLE_OPTIONS_SUCCESSFULLY
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
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

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_OTHER_FLEXIBLE_OPTIONS_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_OTHER_FLEXIBLE_OPTIONS_FAILED, GET_OTHER_FLEXIBLE_OPTIONS_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })

    }

    private fun initChips() {
        OtherFlexibleOptionsChipGroupHelper(layoutInflater, binding.otherOptionsChipGroup)
        viewModel.requestOtherFlexibleOptions()
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
        // 저장하기 위에, ListLiveData에 남아있는 선택값들을 클리어하고 UI에서 선택된 값들을 새로 set 해준다.
        viewModel.otherFlexibleOptions.clear()
        val checkChipIds: List<Int> = binding.otherOptionsChipGroup.chipGroup.checkedChipIds
        checkChipIds.forEach { chipId ->
            val chipText = binding.otherOptionsChipGroup.chipGroup.findViewById<Chip>(chipId)?.text
            chipText?.let {
                viewModel.otherFlexibleOptions.add(it.toString())
            }
        }
        viewModel.saveOtherFlexibleOptions()
    }

    companion object {
        private const val TAG = "EditOtherFlexibleOptionsFragment"

        fun newInstance() = EditOtherFlexibleOptionsFragment()
    }
}