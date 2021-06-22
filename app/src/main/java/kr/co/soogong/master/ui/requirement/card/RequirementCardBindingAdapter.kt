package kr.co.soogong.master.ui.requirement.card

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.widget.AmountView
import java.util.*

@BindingAdapter("bind:requirement_status", "bind:first_datetime_to_string")
fun TextView.setFirstDate(status: String?, date: Date?) {
    // 최초 요청일 : 2021.01.11 - 13:20
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    date?.let {
        text = when (status) {
            RequirementStatus.Requested.toString(), RequirementStatus.Estimated.toString() -> {
                context.getString(
                    R.string.requirements_card_start_time,
                    simpleDateFormat.format(it)
                )
            }
            RequirementStatus.Repairing.toString() -> {
                context.getString(
                    R.string.requirements_card_matched_time,
                    simpleDateFormat.format(it)
                )
            }
            RequirementStatus.RequestFinish.toString() -> {
                context.getString(
                    R.string.requirements_card_request_finish_time,
                    simpleDateFormat.format(it)
                )
            }
            RequirementStatus.Done.toString() -> {
                context.getString(
                    R.string.requirements_card_repair_done_time,
                    simpleDateFormat.format(it)
                )
            }
            else -> {
                context.getString(
                    R.string.requirements_card_start_time,
                    simpleDateFormat.format(it)
                )
            }
        }
    }
}

@BindingAdapter("bind:second_datetime_to_string")
fun TextView.setSecondDate(date: Date?) {
    // 견적 마감일 : 2021.01.11 - 13:20
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    date?.let {
        text = context.getString(R.string.requirements_card_end_time, simpleDateFormat.format(it))
    }
}

@BindingAdapter("bind:amount")
fun AmountView.setAmount(status: String?, price: String?) {
    title = when (status) {
        RequirementStatus.Estimated.toString(), RequirementStatus.Repairing.toString() -> {
            context.getString(R.string.requirements_card_amount_title)
        }
        RequirementStatus.Done.toString(), RequirementStatus.Closed.toString() -> {
            context.getString(R.string.requirements_card_amount_done_title)
        }
        else -> {
            ""
        }
    }
    price?.let { detail = "${DecimalFormat("#,###").parse(it)}원" }
}