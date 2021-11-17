@file:JvmName("FilledTextExt")

package kr.co.soogong.master.utility.extension

import androidx.databinding.BindingAdapter
import kr.co.soogong.master.data.common.Theme
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.atomic.atoms.FilledText

@BindingAdapter("setThemeFromRequirementCard")
fun FilledText.setThemeFromRequirement(requirementCard: RequirementCard) {
    this.theme = when (requirementCard.status) {
        is RequirementStatus.Requested, RequirementStatus.RequestMeasure, RequirementStatus.Measuring, RequirementStatus.Repairing -> Theme.Blue
        else -> Theme.Grey
    }
}

@BindingAdapter("setThemeFromRequirement")
fun FilledText.setThemeFromRequirement(requirement: RequirementDto) {
    this.theme = when (RequirementStatus.getStatusFromRequirement(requirement)) {
        is RequirementStatus.Requested, RequirementStatus.RequestMeasure, RequirementStatus.Measuring, RequirementStatus.Repairing -> Theme.Blue
        else -> Theme.Grey
    }
}