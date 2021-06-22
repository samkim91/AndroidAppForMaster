package kr.co.soogong.master.ui.requirement.action

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.widget.AmountView
import java.util.*

@BindingAdapter("bind:requested_date")
fun TextView.setRequestedDate(date: Date?) {
    // 최초 요청일 : 2021.01.11 - 13:20
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    date?.let {
        text = context.getString(R.string.requirements_card_start_time, simpleDateFormat.format(it))
    }
}

@BindingAdapter("bind:closed_date")
fun TextView.setClosedDate(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    val c = Calendar.getInstance()
    date?.let {
        c.time = date
        c.add(Calendar.DATE, 1)
        text = simpleDateFormat.format(c.time)
    }
}

