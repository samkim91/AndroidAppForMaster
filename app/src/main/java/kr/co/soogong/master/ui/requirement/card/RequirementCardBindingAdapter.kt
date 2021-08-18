package kr.co.soogong.master.ui.requirement.card

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.*
import kr.co.soogong.master.ui.widget.AmountView
import java.util.*

@BindingAdapter("bind:requirement_status", "bind:first_datetime_to_string")
fun TextView.setFirstDate(status: RequirementStatus?, date: Date?) {
    // 최초 요청일 : 2021.01.11 - 13:20
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    date?.let {
        text = when (status) {
            Requested, Estimated -> {
                context.getString(
                    R.string.requirements_card_start_time,
                    simpleDateFormat.format(it)
                )
            }
            Repairing -> {
                context.getString(
                    R.string.requirements_card_matched_time,
                    simpleDateFormat.format(it)
                )
            }
            RequestFinish -> {
                context.getString(
                    R.string.requirements_card_request_finish_time,
                    simpleDateFormat.format(it)
                )
            }
            Done -> {
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

@BindingAdapter(value = ["setRequirementStatus", "priceAmount"])
fun AmountView.setAmount(status: RequirementStatus?, priceAmount: Int?) {
    title = when (status) {
        Estimated, Repairing -> {
            context.getString(R.string.requirements_card_amount_title)
        }
        Done, Closed -> {
            context.getString(R.string.requirements_card_amount_done_title)
        }
        else -> {
            ""
        }
    }
    priceAmount?.let {
        detail =
            if (it != 0) "${DecimalFormat("#,###").format(it)}원"
            else context.getString(R.string.not_estimated_text)
    }
}