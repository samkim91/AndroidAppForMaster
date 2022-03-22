package kr.co.soogong.master.presentation.ui.requirement.action.view

import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import timber.log.Timber

fun setClientPhoneNumberVisibility(
    binding: ActivityViewRequirementBinding,
    requirement: Requirement,
) {
    Timber.tag("ViewRequirementActivity").d("setActionBar: ")

    with(binding) {
        when (requirement.status) {
            is RequirementStatus.RequestConsult, RequirementStatus.Estimated, RequirementStatus.Repairing, RequirementStatus.Measuring, RequirementStatus.Measured ->
                scaClientPhoneNumber.isVisible = true
            is RequirementStatus.Requested, RequirementStatus.Done, RequirementStatus.Closed, RequirementStatus.RequestMeasure, RequirementStatus.Canceled ->
                scaClientPhoneNumber.isVisible = false
        }
    }
}