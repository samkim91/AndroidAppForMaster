package kr.co.soogong.master.ui.profile.detail.otherflexibleoption

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.atomic.molecules.TitleChipGroup
import kr.co.soogong.master.data.model.profile.*
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
        chipGroup: TitleChipGroup,
    ) {
        chipGroup.chipGroup.isSingleSelection = false

        otherFlexibleOption.map { item ->
            val chip = layoutInflater.inflate(R.layout.chip_choice_rectangular,
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