package kr.co.soogong.master.ui.profile.detail.otherflexibleoption

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.*
import kr.co.soogong.master.ui.widget.TitleChipGroup
import kr.co.soogong.master.utility.extension.dp

object OtherFlexibleOptionChipGroupHelper {
    private val otherFlexibleOption =
        listOf(
            MaskCodeTable.inKorean,
            OvershoesCodeTable.inKorean,
            DisposalCodeTable.inKorean,
            ElevatorProtectionCodeTable.inKorean,
            IntimeCodeTable.inKorean,
            AsCodeTable.inKorean,
            NoiseCodeTable.inKorean,
        )

    operator fun invoke(
        layoutInflater: LayoutInflater,
        chipGroup: TitleChipGroup
    ) {
        chipGroup.chipGroup.isSingleSelection = false

        otherFlexibleOption.map { item ->
            val chip = layoutInflater.inflate(R.layout.single_chip_choice_rectangular_layout, null) as Chip
            chip.text = item
            chip.minWidth = 156.dp
            chip.minHeight = 40.dp
            chip.setTextAppearanceResource(R.style.small_text_style_medium)

            chipGroup.addChip(chip)
        }
    }
}