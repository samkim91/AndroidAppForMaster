package kr.co.soogong.master.ui.requirement.action

import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementStatus
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
    // 2022.01.11 - 13:20
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
    date?.let {
        text = simpleDateFormat.format(it)
    }
}

@BindingAdapter("bind:status")
fun TextView.setStatus(requirementDto: RequirementDto?) {
    // Code -> Status in Korean
    requirementDto?.let {
        text = RequirementStatus.getStatus(it).toString()
    }
}

