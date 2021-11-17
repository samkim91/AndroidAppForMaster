@file:JvmName("FilledTextExt")

package kr.co.soogong.master.utility.extension

import androidx.databinding.BindingAdapter
import kr.co.soogong.master.atomic.atoms.FilledLabel
import kr.co.soogong.master.data.common.ColorTheme
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus

@BindingAdapter("setThemeFromRequirementCard")
fun FilledLabel.setThemeFromRequirement(requirementCard: RequirementCard?) {
    this.colorTheme = when (requirementCard?.status) {
        is RequirementStatus.Requested, RequirementStatus.RequestMeasure, RequirementStatus.Measuring, RequirementStatus.Repairing -> ColorTheme.Blue
        else -> ColorTheme.Grey
    }
}

@BindingAdapter("setThemeFromRequirement")
fun FilledLabel.setThemeFromRequirement(requirement: RequirementDto?) {
    this.colorTheme = when (RequirementStatus.getStatusFromRequirement(requirement)) {
        is RequirementStatus.Requested, RequirementStatus.RequestMeasure, RequirementStatus.Measuring, RequirementStatus.Repairing -> ColorTheme.Blue
        else -> ColorTheme.Grey
    }
}

@BindingAdapter("setContentFromRequirement")
fun FilledLabel.setContentFromRequirement(requirement: RequirementDto?) {
    this.content = RequirementStatus.getStatusFromRequirement(requirement).inKorean
}