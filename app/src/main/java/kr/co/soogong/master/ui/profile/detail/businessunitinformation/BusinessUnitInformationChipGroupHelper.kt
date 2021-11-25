package kr.co.soogong.master.ui.profile.detail.businessunitinformation

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.atomic.molecules.TitleChipGroup
import kr.co.soogong.master.utility.extension.dp

object BusinessUnitInformationChipGroupHelper {
    private val businessUnitInformation =
        listOf("개인사업자", "법인사업자", "프리랜서")

    operator fun invoke(
        layoutInflater: LayoutInflater,
        chipGroup: TitleChipGroup,
    ) {
        chipGroup.chipGroup.isSingleSelection = true
        chipGroup.chipGroup.isSelectionRequired = true

        businessUnitInformation.map { item ->
            val chip = layoutInflater.inflate(R.layout.single_chip_choice_rectangular_layout,
                chipGroup,
                false) as Chip
            chip.text = item
            chip.minWidth = 156.dp
            chip.minHeight = 40.dp
            chip.setTextAppearanceResource(R.style.text_style_14sp_medium)

            chipGroup.addChip(chip)
        }
    }
}