package kr.co.soogong.master.presentation.ui.requirement.action.view

import android.content.Context
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.presentation.atomic.molecules.Title3Container
import kr.co.soogong.master.presentation.atomic.molecules.Title3Container.Companion.CANCEL_TYPE
import kr.co.soogong.master.presentation.atomic.molecules.Title3Container.Companion.ESTIMATION_TYPE
import kr.co.soogong.master.presentation.atomic.molecules.Title3Container.Companion.PREVIOUS_ESTIMATION_TYPE
import kr.co.soogong.master.presentation.atomic.molecules.Title3Container.Companion.REPAIR_TYPE
import kr.co.soogong.master.presentation.atomic.molecules.Title3Container.Companion.REQUIREMENT_TYPE
import kr.co.soogong.master.presentation.atomic.molecules.Title3Container.Companion.REVIEW_TYPE
import timber.log.Timber

fun setFlexibleContainer(
    context: Context,
    binding: ActivityViewRequirementBinding,
    requirement: Requirement,
) {
    Timber.tag("ViewRequirementActivity").d("setFlexibleContainer: ")

    with(binding) {
        flexibleContainer.removeAllViews()

        when (requirement.status) {
            // 방문요청, 방문예정
            is RequirementStatus.RequestMeasure, RequirementStatus.Measuring -> Unit

            // 시공예정
            // view : 나의 제안 내용
            is RequirementStatus.Repairing -> {
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = ESTIMATION_TYPE,
                )
            }

            // 시공완료
            // view : 나의 최종 시공 내용
            is RequirementStatus.Done -> {
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = REPAIR_TYPE,
                )
            }

            // 평가완료
            // view : 고객 리뷰, 나의 최종 시공 내용
            is RequirementStatus.Closed -> {
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = REVIEW_TYPE,
                )
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = REPAIR_TYPE,
                )
            }

            // 시공취소
            // view : 시공 취소 사유
            is RequirementStatus.Canceled -> {
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = CANCEL_TYPE,
                )
            }
        }

        // 기본으로 들어가는 정보 : 고객 요청 내용, 이전 실측 내용(있으면)
        Title3Container.addIconLabelContainer(
            context = context,
            container = flexibleContainer,
            requirement = requirement,
            contentType = REQUIREMENT_TYPE,
        )
        requirement.measurement?.let {
            Title3Container.addIconLabelContainer(
                context = context,
                container = flexibleContainer,
                requirement = requirement,
                contentType = PREVIOUS_ESTIMATION_TYPE,
            )
        }
    }
}