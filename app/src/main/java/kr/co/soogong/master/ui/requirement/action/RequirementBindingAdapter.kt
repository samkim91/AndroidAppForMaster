package kr.co.soogong.master.ui.requirement.action

import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.GRAY_THEME
import kr.co.soogong.master.ui.GREEN_THEME
import kr.co.soogong.master.ui.widget.RequirementIntro
import kr.co.soogong.master.utility.extension.formatFullDateTime

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
        RequirementStatus.Requested -> {
            title = context.getString(R.string.requirement_intro_title_of_requested)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_requested)
            theme = GREEN_THEME
        }
        RequirementStatus.Estimated -> {
            title = context.getString(R.string.requirement_intro_title_of_estimated)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_estimated)
            theme = GRAY_THEME
        }
        RequirementStatus.RequestConsult -> {
            title = context.getString(R.string.requirement_intro_title_of_request_consult)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_request_consult)
            theme = GREEN_THEME
        }
        RequirementStatus.RequestMeasure -> {
            title = context.getString(R.string.requirement_intro_title_of_request_measure)
            subtitle = ""
            theme = GREEN_THEME
        }
        RequirementStatus.Repairing -> {
            title = context.getString(R.string.requirement_intro_title_of_repairing_from_funnel)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_repairing)
            theme = GREEN_THEME
        }
        RequirementStatus.Measuring -> {
            title = context.getString(R.string.requirement_intro_title_of_measuring)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_measuring)
            theme = GREEN_THEME
        }
        RequirementStatus.Measured -> {
            title = context.getString(R.string.requirement_intro_title_of_measured)
            subtitle = ""
            theme = GREEN_THEME
        }
        RequirementStatus.Done -> {
            title = context.getString(R.string.requirement_intro_title_of_done)
            subtitle = context.getString(R.string.requirement_intro_subtitle_of_done)
            theme = GREEN_THEME
        }
        RequirementStatus.Closed -> {
            title = context.getString(
                R.string.requirement_intro_title_of_close,
                requirementDto?.estimationDto?.repair?.review?.createdAt.formatFullDateTime()
            )
            subtitle = ""
            theme = GRAY_THEME
        }
        RequirementStatus.Canceled -> {
            title = context.getString(R.string.requirement_intro_title_of_canceled)
            subtitle = ""
            theme = GRAY_THEME
        }
    }
}