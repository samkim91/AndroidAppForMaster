package kr.co.soogong.master.ui.requirement.action.cancel

import android.content.Context
import android.content.res.ColorStateList
import android.widget.RadioButton
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.common.Code
import kr.co.soogong.master.ui.widget.TitleRadioGroup
import kr.co.soogong.master.utility.extension.dp

object CanceledReasonRadioGroupHelper {
    operator fun invoke(
        context: Context,
        radioGroup: TitleRadioGroup,
        canceledReasons: List<Code>,
    ) {
        canceledReasons.map { reason ->
            RadioButton(context).apply {
                text = reason.name
                height = 34.dp
                setTextAppearance(R.style.text_style_14sp_medium)
                setTextColor(context.resources.getColor(R.color.text_basic_color, null))
                buttonTintList = ColorStateList.valueOf(context.getColor(R.color.color_22D47B))
                radioGroup.addRadioButton(this)
            }
        }

        // 초기값 설정
        (radioGroup.radioGroup.getChildAt(0) as RadioButton).isChecked = true
    }
}