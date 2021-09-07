package kr.co.soogong.master.ui.requirement.action

import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.*
import kr.co.soogong.master.ui.GRAY_THEME
import kr.co.soogong.master.ui.GREEN_THEME
import kr.co.soogong.master.ui.widget.RequirementIntro
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
        text = RequirementStatus.getStatusFromRequirement(it).inKorean
    }
}

@BindingAdapter("initByRequirement")
fun RequirementIntro.initByRequirement(requirementDto: RequirementDto?) {
    when (RequirementStatus.getStatusFromRequirement(requirementDto)) {
        Requested -> {
            title = context.getString(R.string.requirement_intro_title_of_requested)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_requested)
            theme = GREEN_THEME
        }
        Estimated -> {
            title = context.getString(R.string.requirement_intro_title_of_estimated)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_estimated)
            theme = GRAY_THEME
        }
        RequestMeasure -> {
            title = context.getString(R.string.requirement_intro_title_of_request_measure, SimpleDateFormat("yyyy.MM.dd (E) - HH:mm", Locale.KOREA).format(requirementDto?.measuringDate))
            subtitle = ""
            theme = GREEN_THEME
        }
        Repairing -> {
            // TODO: 2021/08/23 수공비서건인지 아닌지에 따라 분기 필요.. 수공비서건은 시공 예정일 추가
            title = context.getString(R.string.requirement_intro_title_of_repairing_from_funnel)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_repairing)
            theme = GREEN_THEME
        }
        RequestFinish -> {
            title = context.getString(R.string.requirement_intro_title_of_request_finish)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_request_finish)
            theme = GREEN_THEME
        }
        Measuring -> {
            title = context.getString(R.string.requirement_intro_title_of_measuring, SimpleDateFormat("yyyy.MM.dd (E) - HH:mm", Locale.KOREA).format(requirementDto?.measuringDate))
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_measuring)
            theme = GREEN_THEME
        }
        Measured -> {
            title = context.getString(R.string.requirement_intro_title_of_measured)
            subtitle = ""
            theme = GREEN_THEME
        }
        Done -> {
            title = context.getString(R.string.requirement_intro_title_of_done)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_done)
            theme = GREEN_THEME
        }
        Closed -> {
            title = context.getString(
                R.string.requirement_intro_title_of_close,
                SimpleDateFormat("yyyy.MM.dd (E) - HH:mm", Locale.KOREA).format(requirementDto?.estimationDto?.repair?.review?.createdAt)
            )
            subtitle = ""
            theme = GRAY_THEME
        }
        Canceled -> {
            title = context.getString(R.string.requirement_intro_title_of_canceled)
            subtitle = ""
            theme = GRAY_THEME
        }
        else -> {
            title = ""
            subtitle = ""
            theme = GRAY_THEME
        }
    }
}

