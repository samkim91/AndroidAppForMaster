package kr.co.soogong.master.presentation.ui.requirement.action.view

import android.content.Context
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.domain.entity.common.CodeTable
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

// Note: 1차 리팩토링이고, 중복되는 함수들은 다시 빼내야함.
fun setFlexibleContainer(
    context: Context,
    binding: ActivityViewRequirementBinding,
    requirement: Requirement,
) {
    Timber.tag("ViewRequirementActivity").d("setFlexibleContainer: ")

    with(binding) {
        flexibleContainer.removeAllViews()

        when (requirement.status) {
            // 견적요청, 실측요청
            // view : 고객 요청 내용, 이전 실측 내용(있으면)
            is RequirementStatus.Requested, RequirementStatus.RequestMeasure -> {
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = REQUIREMENT_TYPE)
                requirement.measurement?.let {
                    Title3Container.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirement = requirement,
                        contentType = PREVIOUS_ESTIMATION_TYPE)
                }
            }

            // 상담요청
            // view : 나의 제안 내용(있으면), 고객 요청 내용, 이전 실측 내용(있으면)
            is RequirementStatus.RequestConsult -> {
                if (requirement.estimation.masterResponseCode == CodeTable.ACCEPTED) {
                    Title3Container.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirement = requirement,
                        contentType = ESTIMATION_TYPE,
                    )
                }
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

            // 매칭대기, 실측완료
            // view : 나의 제안 내용, 고객 요청 내용, 이전 실측 내용
            is RequirementStatus.Estimated, RequirementStatus.Measured -> {
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = ESTIMATION_TYPE,
                )
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

            // 시공예정, 실측예정
            // view : 고객 요청 내용, 이전 실측 내용
            is RequirementStatus.Repairing, RequirementStatus.Measuring -> {
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

            // 시공완료
            // view : 나의 최종 시공 내용, 고객 요청 내용, 이전 실측 내용(있으면)
            is RequirementStatus.Done -> {
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = REPAIR_TYPE,
                )
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

            // 평가완료
            // view : 고객 리뷰, 나의 최종 시공 내용, 고객 요청 내용, 이전 실측 내용(있으면)
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

            // 시공취소
            // view : 시공 취소 사유, 고객 요청 내용, 이전 실측 내용(있으면)
            is RequirementStatus.Canceled -> {
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = CANCEL_TYPE,
                )
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
    }
}