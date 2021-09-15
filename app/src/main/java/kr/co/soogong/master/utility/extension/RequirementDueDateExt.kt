@file:JvmName("RequirementDueDateExt")

package kr.co.soogong.master.utility.extension

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequestConsult
import kr.co.soogong.master.data.model.requirement.RequestMeasure
import kr.co.soogong.master.data.model.requirement.Requested
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.widget.RequirementDueDate
import java.util.*

@BindingAdapter("setDueDate")
fun RequirementDueDate.setDueDate(requirementDto: RequirementDto?) {
    requirementDto?.let {
        when (RequirementStatus.getStatusFromRequirement(it)) {
            Requested, RequestConsult -> {
                this.isVisible = true
                this.bringToFront()
                label = context.getString(R.string.requirements_card_due_date)
                date = getEstimationDueDate(it.createdAt)
            }
            RequestMeasure -> {
                this.isVisible = true
                this.bringToFront()
                label = context.getString(R.string.requirements_card_response_measure_due_date)
                date = getMeasureDueDate(it.createdAt)
            }
            else -> {
                this.isVisible = false
            }
        }
    }
}

fun getEstimationDueDate(createdAt: Date?): Date {
    val calendar = Calendar.getInstance(Locale.KOREA)
    createdAt?.let {
        calendar.time = it
        calendar.add(Calendar.DATE, 1)
    }
    return calendar.time
}

fun getMeasureDueDate(createdAt: Date?): Date {
    val calendar = Calendar.getInstance(Locale.KOREA)
    createdAt?.let {
        calendar.time = it
        calendar.add(Calendar.HOUR, 1)
    }
    return calendar.time
}
