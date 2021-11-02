package kr.co.soogong.master.ui.profile.detail.otherflexibleoption

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.model.profile.CodeTable
import kr.co.soogong.master.data.model.profile.OtherFlexibleOptionCodeTable
import kr.co.soogong.master.databinding.FragmentEditOtherFlexibleOptionBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.SAVE_MASTER_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditOtherFlexibleOptionFragment : BaseFragment<FragmentEditOtherFlexibleOptionBinding>(
    R.layout.fragment_edit_other_flexible_option
) {
    private val viewModel: EditOtherFlexibleOptionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        initChipsGroup()

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            defaultButton.setOnClickListener {
                saveCheckedChips()
            }
        }
    }

    private fun initChipsGroup() {
        OtherFlexibleOptionChipGroupHelper(layoutInflater, binding.otherOptionsChipGroup)
        viewModel.requestOtherFlexibleOption()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        // 가져온 text 들을 chip group 에서 찾아서 selected 표시를 해준다.
        viewModel.otherFlexibleOption.observe(viewLifecycleOwner, { masterConfigList ->
            masterConfigList.map { masterConfig ->
                binding.otherOptionsChipGroup.chipGroup.children.forEach {
                    val chip = it as? Chip
                    if (chip?.text.toString() == masterConfig.name && masterConfig.value == "1") {
                        chip?.isChecked = true
                    }
                }
            }
        })

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_MASTER_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                REQUEST_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun saveCheckedChips() {
        bind {
            viewModel.otherFlexibleOption.value = otherOptionsChipGroup.chipGroup.children.map {
                val chip = otherOptionsChipGroup.chipGroup.findViewById<Chip>(it.id)

                MasterConfigDto(
                    id = viewModel.otherFlexibleOption.value?.find { masterConfigDto -> masterConfigDto.name == chip.text.toString() }?.id,
                    groupCode = OtherFlexibleOptionCodeTable.code,
                    code = CodeTable.findCodeTableByKorean(chip.text.toString()).code,
                    name = chip.text.toString(),
                    value = if (chip.isChecked) "1" else "0"
                )
            }.toList()

            viewModel.saveOtherFlexibleOption()
        }
    }

    companion object {
        private const val TAG = "EditOtherFlexibleOptionsFragment"

        fun newInstance() = EditOtherFlexibleOptionFragment()
    }
}