package kr.co.soogong.master.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.ui.widget.TitleChipGroup

object BusinessTypeChipGroupHelper {

    fun makeEntryChipGroupWithSubTitle(
        layoutInflater: LayoutInflater,
        item: BusinessType,
        closeClickListener: (titleChipGroup: TitleChipGroup, chip: View, itemId: Int) -> Unit
    ): ViewGroup {
        val titleChipGroup = TitleChipGroup(layoutInflater.context)

        titleChipGroup.titleVisible = false
        titleChipGroup.subTitleVisible = true
        titleChipGroup.subTitle = item.category?.name
        titleChipGroup.blackSubTitle = true

        item.projects?.map { project ->
            val chip = layoutInflater.inflate(R.layout.single_chip_entry_layout, null) as Chip
            chip.setOnCloseIconClickListener {
                closeClickListener(titleChipGroup, it, project.id)
            }
            chip.text = project.name
            chip.setTextAppearanceResource(R.style.small_text_style_medium)

            titleChipGroup.addChip(chip)
        }

        return titleChipGroup
    }


}