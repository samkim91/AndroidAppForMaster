package kr.co.soogong.master.ui.requirement.card

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.model.requirement.Estimation
import kr.co.soogong.master.data.model.requirement.Transmissions
import kr.co.soogong.master.ui.widget.AmountView
import java.util.*

@BindingAdapter("bind:created_datetime_to_string")
fun TextView.setStartDatetime(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    date?.let {
        text = context.getString(
            R.string.requirements_card_start_time,
            simpleDateFormat.format(it)
        )
    }
}

//@BindingAdapter("bind:start_date")
//fun TextView.setStartDate(createdAt: Long?) {
//    val date = Date(createdAt)
//    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
//    text = context.getString(
//        R.string.requirements_card_start_time,
//        simpleDateFormat.format(date)
//    )
//}

@BindingAdapter("bind:closed_datetime_to_string")
fun TextView.setEndDatetime(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    val c = Calendar.getInstance()
    date?.let {
        c.time = date
        c.add(Calendar.DATE, 1)
        text = context.getString(R.string.requirements_card_end_time, simpleDateFormat.format(c.time))
    }
}

//@BindingAdapter("bind:end_date")
//fun TextView.setEndDate(createdAt: Long?) {
//    val date = Date(createdAt ?: System.currentTimeMillis())
//    val c = Calendar.getInstance()
//    c.time = date
//    c.add(Calendar.DATE, 1)
//    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
//    text = context.getString(R.string.requirements_card_end_time, simpleDateFormat.format(c.time))
//}

@BindingAdapter("bind:closed_datetime_only_to_string")
fun TextView.setEndDatetimeOnly(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    val c = Calendar.getInstance()
    date?.let {
        c.time = date
        c.add(Calendar.DATE, 1)
        text = simpleDateFormat.format(c.time)
    }
}
//
//@BindingAdapter("bind:end_date_2")
//fun TextView.setEndDate2(createdAt: Long?) {
//    val date = Date(createdAt ?: System.currentTimeMillis())
//    val c = Calendar.getInstance()
//    c.time = date
//    c.add(Calendar.DATE, 1)
//    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
//    text = simpleDateFormat.format(c.time)
//}

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