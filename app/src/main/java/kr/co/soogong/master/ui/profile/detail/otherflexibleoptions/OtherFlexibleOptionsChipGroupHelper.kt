package kr.co.soogong.master.ui.profile.detail.otherflexibleoptions

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.ui.widget.TitleChipGroup
import kr.co.soogong.master.utility.extension.dp

object OtherFlexibleOptionsChipGroupHelper {
    private val otherFlexibleOptions =
        listOf("마스크 착용", "덧신 착용", "시공 쓰레기 처리", "엘리베이터 보양작업", "약속시간 준수", "마감 불량 시 재시공", "시끄러울 수 있음")

    operator fun invoke(
        layoutInflater: LayoutInflater,
        chipGroup: TitleChipGroup
    ) {
        chipGroup.chipGroup.isSingleSelection = false

        otherFlexibleOptions.map { item ->
            val chip = layoutInflater.inflate(R.layout.single_chip_choice_layout, null) as Chip
            chip.text = item
            chip.minWidth = 156.dp
            chip.minHeight = 40.dp
            chip.setTextAppearanceResource(R.style.small_text_style_medium)

            chipGroup.addChip(chip)
        }
    }
}