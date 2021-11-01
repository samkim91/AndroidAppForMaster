@file:JvmName("RequirementDueDateExt")

package kr.co.soogong.master.utility.extension

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.ui.widget.RequirementDueDate
import kr.co.soogong.master.utility.TimeHelper
import kr.co.soogong.master.utility.TimeHelper.WITHIN_24_HOURS
import kr.co.soogong.master.utility.TimeHelper.WITHIN_90_MINUTES

@BindingAdapter("setDueDate")
fun RequirementDueDate.setDueDate(requirementDto: RequirementDto?) {
    requirementDto?.let {
        when (RequirementStatus.getStatusFromRequirement(it)) {
            is RequirementStatus.Requested, RequirementStatus.RequestConsult -> {
                (requirementDto.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED).let { accepted ->
                    if (accepted) {
                        this.isVisible = false
                        return
                    }
                    this.isVisible = true
                    label = context.getString(R.string.requirements_card_due_date)
                    date = TimeHelper.getDueTime(it.createdAt, WITHIN_24_HOURS)
                }
            }
            is RequirementStatus.RequestMeasure -> {
                this.isVisible = true
                label = context.getString(R.string.requirements_card_response_measure_due_date)
                date = TimeHelper.getDueTime(requirementDto.estimationDto?.createdAt,
                    WITHIN_90_MINUTES)
            }
            else -> {
                this.isVisible = false
            }
        }
    }
}
