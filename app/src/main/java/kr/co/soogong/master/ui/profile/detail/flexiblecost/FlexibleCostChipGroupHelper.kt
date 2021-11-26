package kr.co.soogong.master.ui.profile.detail.flexiblecost

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.atomic.molecules.TitleChipGroup
import kr.co.soogong.master.utility.extension.dp

object FlexibleCostChipGroupHelper {
    const val TRAVEL_COST = "TRAVEL_COST"
    const val CRANE_USAGE = "CRANE_USAGE"
    const val PACKAGE_COST = "PACKAGE_COST"

    private val travelCostItems = listOf("있어요", "없어요")
    private val craneUsageItems = listOf("사용 안함", "2층 이상", "엘리베이터 사용 불가시")
    private val packageCostItems = listOf("있어요", "없어요")

    operator fun invoke(
        layoutInflater: LayoutInflater,
        chipGroup: TitleChipGroup,
        category: String,
    ) {
        val items = when (category) {
            TRAVEL_COST -> travelCostItems
            CRANE_USAGE -> craneUsageItems
            else -> packageCostItems
        }

        items.map { item ->
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