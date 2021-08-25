@file:JvmName("RequirementDueDateExt")

package kr.co.soogong.master.utility.extension

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequestMeasure
import kr.co.soogong.master.data.model.requirement.Requested
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.widget.RequirementDueDate

@BindingAdapter("setDueDate")
fun RequirementDueDate.setDueDate(requirementDto: RequirementDto?) {
    requirementDto?.let {
        when (RequirementStatus.getStatusFromRequirement(it)) {
            Requested -> {
                this.isVisible = true
                this.bringToFront()
                label = context.getString(R.string.requirements_card_due_date)
                date = it.closedAt
            }
            // TODO: 2021/08/23 수공비서에서 추가된 상태값(실측 제한) 관련 조건 추가
            RequestMeasure -> {
                this.isVisible = true
                this.bringToFront()
                label = context.getString(R.string.requirements_card_response_measure_due_date)
                date = it.closedAt
            }
            else -> {
                this.isVisible = false
            }
        }
    }
}
