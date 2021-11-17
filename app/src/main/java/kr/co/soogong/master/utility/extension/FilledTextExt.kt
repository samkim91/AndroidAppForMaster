@file:JvmName("FilledTextExt")

package kr.co.soogong.master.utility.extension

import androidx.databinding.BindingAdapter
import kr.co.soogong.master.data.common.Theme
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.atomic.atoms.FilledLabel

@BindingAdapter("setThemeFromRequirementCard")
fun FilledLabel.setThemeFromRequirement(requirementCard: RequirementCard?) {
    this.theme = when (requirementCard?.status) {
        is RequirementStatus.Requested, RequirementStatus.RequestMeasure, RequirementStatus.Measuring, RequirementStatus.Repairing -> Theme.Blue
        else -> Theme.Grey
    }
}

@BindingAdapter("setThemeFromRequirement")
fun FilledLabel.setThemeFromRequirement(requirement: RequirementDto?) {
    this.theme = when (RequirementStatus.getStatusFromRequirement(requirement)) {
        is RequirementStatus.Requested, RequirementStatus.RequestMeasure, RequirementStatus.Measuring, RequirementStatus.Repairing -> Theme.Blue
        else -> Theme.Grey
    }
}

@BindingAdapter("setContentFromRequirement")
fun FilledLabel.setContentFromRequirement(requirement: RequirementDto?) {
    this.content = RequirementStatus.getStatusFromRequirement(requirement).inKorean
}