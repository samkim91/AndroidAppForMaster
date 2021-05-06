package kr.co.soogong.master.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.ui.widget.TitleChipGroup

object BusinessTypeChipGroupHelper {
    const val CHOICE_CHIP_RECTANGULAR = R.layout.single_chip_choice_layout
    const val ENTRY_CHIP_ROUND = R.layout.single_chip_entry_layout

    fun makeChipGroupWithTitle(
        layoutInflater: LayoutInflater,
        item: BusinessType,
        chipStyle: Int,
        closeClickListener: (titleChipGroup: TitleChipGroup, chip: View, projectId: Int) -> Unit
    ): ViewGroup {
        val titleChipGroup = TitleChipGroup(layoutInflater.context)

        titleChipGroup.title = item.category?.name

        item.projects?.map { project ->
            val chip = layoutInflater.inflate(chipStyle, null) as Chip
            chip.setOnCloseIconClickListener {
                closeClickListener(titleChipGroup, it, project.id)
            }
            chip.text = project.name

            titleChipGroup.addChip(chip)
        }

        return titleChipGroup
    }


}