package kr.co.soogong.master.presentation.ui.requirement

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus

object RequirementChipGroupHelper {
    operator fun invoke(
        layoutInflater: LayoutInflater,
        chipGroup: ChipGroup,
        status: List<RequirementStatus>,
    ) {
        chipGroup.isSingleSelection = true
        chipGroup.isSelectionRequired = true

        val whole = layoutInflater.inflate(R.layout.chip_choice_rounded,
            chipGroup,
            false) as Chip
        whole.text = "전체"
        whole.isChecked = true
        chipGroup.addView(whole)

        status.map { item ->
            val chip =
                layoutInflater.inflate(R.layout.chip_choice_rounded,
                    chipGroup,
                    false) as Chip
            chip.text = item.inKorean

            chipGroup.addView(chip)
        }
    }
}