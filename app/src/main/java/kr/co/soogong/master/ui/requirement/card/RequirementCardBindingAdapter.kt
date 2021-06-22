package kr.co.soogong.master.ui.requirement.card

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.ui.widget.AmountView
import java.util.*

@BindingAdapter("bind:created_datetime_to_string")
fun TextView.setStartDatetime(date: Date?) {
    // 최초 요청일 : 2021.01.11 - 13:20
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    date?.let {
        text = context.getString(
            R.string.requirements_card_start_time,
            simpleDateFormat.format(it)
        )
    }
}

@BindingAdapter("bind:closed_datetime_to_string")
fun TextView.setEndDatetime(date: Date?) {
    // 견적 마감일 : 2021.01.11 - 13:20
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    val c = Calendar.getInstance()
    date?.let {
        c.time = date
        c.add(Calendar.DATE, 1)
        text = context.getString(R.string.requirements_card_end_time, simpleDateFormat.format(c.time))
    }
}

@BindingAdapter("bind:closed_datetime_only_to_string")
fun TextView.setEndDatetimeOnly(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    val c = Calendar.getInstance()
    date?.let {
        c.time = date
        c.add(Calendar.DATE, 1)
        text = simpleDateFormat.format(c.time)
    }
}

@BindingAdapter("bind:requirementStatus")
fun AmountView.setAmount(status: String?, price: String?) {
    title = when (status) {
        "Estimated", "Repairing" -> {
            context.getString(R.string.requirements_card_amount_title)
        }
        "Done", "Closed" -> {
            context.getString(R.string.requirements_card_amount_done_title)
        }
        else -> {
            ""
        }
    }

    detail = "${DecimalFormat("#,###").format(price)}원"
}