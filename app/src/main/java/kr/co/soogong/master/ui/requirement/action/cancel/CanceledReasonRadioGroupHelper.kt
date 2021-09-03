package kr.co.soogong.master.ui.requirement.action.cancel

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatRadioButton
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.CompareCodeTable
import kr.co.soogong.master.data.model.requirement.repair.*
import kr.co.soogong.master.ui.widget.TitleChipGroup
import kr.co.soogong.master.ui.widget.TitleRadioGroup
import kr.co.soogong.master.utility.extension.dp

object CanceledReasonRadioGroupHelper {
    val canceledReasons = listOf(
        OutOfContactClient,
        FailOfNegotiations,
        NotOnSchedule,
        RefuseOfClient,
        ImpossibleRepair,
        EtcCanceledReason,
    )

    operator fun invoke(
        context: Context,
        radioGroup: TitleRadioGroup,
    ) {
        canceledReasons.map { canceledReason ->
            val radio = RadioButton(context)
            radio.text = canceledReason.inKorean
            radio.height = 34.dp
            radio.setTextAppearance(R.style.text_style_14sp_medium)
            radio.buttonTintList = ColorStateList.valueOf(context.getColor(R.color.color_22D47B))
            radioGroup.addRadioButton(radio)
        }
        
        (radioGroup.radioGroup.getChildAt(0) as RadioButton).isChecked = true
    }
}