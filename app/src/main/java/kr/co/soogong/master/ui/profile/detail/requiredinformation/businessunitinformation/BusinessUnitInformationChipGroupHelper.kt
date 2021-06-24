package kr.co.soogong.master.ui.profile.detail.requiredinformation.businessunitinformation

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.ui.widget.TitleChipGroup
import kr.co.soogong.master.utility.extension.dp

object BusinessUnitInformationChipGroupHelper {
    private val businessUnitInformation =
        listOf("개인사업자", "법인사업자", "프리랜서")

    operator fun invoke(
        layoutInflater: LayoutInflater,
        chipGroup: TitleChipGroup
    ) {
        chipGroup.chipGroup.isSingleSelection = true

        businessUnitInformation.map { item ->
            val chip = layoutInflater.inflate(R.layout.single_chip_choice_layout, null) as Chip
            chip.text = item
            chip.minWidth = 156.dp
            chip.minHeight = 40.dp
            chip.setTextAppearanceResource(R.style.small_text_style_medium)

            chipGroup.addChip(chip)
        }
    }
}