package kr.co.soogong.master.ui.requirement.card

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.Transmissions
import kr.co.soogong.master.data.model.requirement.EstimationStatus
import kr.co.soogong.master.ui.widget.AmountView
import java.util.*

@BindingAdapter("bind:estimation_status")
fun TextView.setEstimationStatus(estimationStatus: EstimationStatus?) {
    text = estimationStatus?.toString() ?: ""
}

@BindingAdapter("bind:start_date")
fun TextView.setStartDate(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    text = context.getString(
        R.string.requirements_card_start_time,
        simpleDateFormat.format(date ?: System.currentTimeMillis())
    )
}

@BindingAdapter("bind:start_date")
fun TextView.setStartDate(createdAt: Long?) {
    val date = Date(createdAt ?: System.currentTimeMillis())
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    text = context.getString(
        R.string.requirements_card_start_time,
        simpleDateFormat.format(date)
    )
}

@BindingAdapter("bind:end_date")
fun TextView.setEndDate(date: Date?) {
    val c = Calendar.getInstance()
    c.time = date ?: Date(System.currentTimeMillis())
    c.add(Calendar.DATE, 1)
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    text = context.getString(R.string.requirements_card_end_time, simpleDateFormat.format(c.time))
}

@BindingAdapter("bind:end_date")
fun TextView.setEndDate(createdAt: Long?) {
    val date = Date(createdAt ?: System.currentTimeMillis())
    val c = Calendar.getInstance()
    c.time = date
    c.add(Calendar.DATE, 1)
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    text = context.getString(R.string.requirements_card_end_time, simpleDateFormat.format(c.time))
}

@BindingAdapter("bind:end_date_2")
fun TextView.setEndDate2(date: Date?) {
    val c = Calendar.getInstance()
    c.time = date ?: Date(System.currentTimeMillis())
    c.add(Calendar.DATE, 1)
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    text = simpleDateFormat.format(c.time)
}

@BindingAdapter("bind:end_date_2")
fun TextView.setEndDate2(createdAt: Long?) {
    val date = Date(createdAt ?: System.currentTimeMillis())
    val c = Calendar.getInstance()
    c.time = date
    c.add(Calendar.DATE, 1)
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - hh:mm")
    text = simpleDateFormat.format(c.time)
}

@BindingAdapter(value = ["bind:estimationStatus", "bind:transmissions"])
fun AmountView.setAmount(status: EstimationStatus, transmissions: Transmissions) {
    title = when (status) {
        EstimationStatus.Waiting, EstimationStatus.Progress -> {
            context.getString(R.string.requirements_card_amount_title)
        }
        EstimationStatus.Final -> {
            context.getString(R.string.requirements_card_amount_done_title)
        }
        else -> {
            ""
        }
    }

    detail = "${DecimalFormat("#,###").format(transmissions.message?.priceInNumber ?: 0)}원"
}