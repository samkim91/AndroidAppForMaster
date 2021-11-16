package kr.co.soogong.master.ui.requirement.action.view

import android.content.Context
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.ui.atomic.molecules.IconLabelContainer
import kr.co.soogong.master.ui.atomic.molecules.IconLabelContainer.Companion.CANCEL_TYPE
import kr.co.soogong.master.ui.atomic.molecules.IconLabelContainer.Companion.ESTIMATION_TYPE
import kr.co.soogong.master.ui.atomic.molecules.IconLabelContainer.Companion.PREVIOUS_ESTIMATION_TYPE
import kr.co.soogong.master.ui.atomic.molecules.IconLabelContainer.Companion.REPAIR_TYPE
import kr.co.soogong.master.ui.atomic.molecules.IconLabelContainer.Companion.REQUIREMENT_TYPE
import kr.co.soogong.master.ui.atomic.molecules.IconLabelContainer.Companion.REVIEW_TYPE
import timber.log.Timber

// Note: 1차 리팩토링이고, 중복되는 함수들은 다시 빼내야함.
fun setFlexibleContainer(
    context: Context,
    binding: ActivityViewRequirementBinding,
    requirement: RequirementDto,
) {
    Timber.tag("ViewRequirementActivity").d("setFlexibleContainer: ")

    with(binding) {
        flexibleContainer.removeAllViews()

        when (RequirementStatus.getStatusFromRequirement(requirement)) {
            is RequirementStatus.Requested, RequirementStatus.RequestMeasure -> {
                // view : 고객 요청 내용, 이전 실측 내용(있으면)
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REQUIREMENT_TYPE)
                requirement.measurement?.let {
                    IconLabelContainer.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = PREVIOUS_ESTIMATION_TYPE)
                }
            }

            is RequirementStatus.RequestConsult -> {
                (requirement.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED).let { accepted ->
                    if (accepted) {
                        // view : 나의 제안 내용
                        IconLabelContainer.addIconLabelContainer(
                            context = context,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = ESTIMATION_TYPE,
                        )
                    }
                    // view : 고객 요청 내용, 이전 실측 내용(있으면)
                    IconLabelContainer.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                    )
                    requirement.measurement?.let {
                        IconLabelContainer.addIconLabelContainer(
                            context = context,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                        )
                    }
                }
            }

            is RequirementStatus.Estimated, RequirementStatus.Measured -> {
                // view : 나의 제안 내용, 고객 요청 내용, 이전 실측 내용
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = ESTIMATION_TYPE,
                )
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REQUIREMENT_TYPE,
                )
                requirement.measurement?.let {
                    IconLabelContainer.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = PREVIOUS_ESTIMATION_TYPE,
                    )
                }
            }

            is RequirementStatus.Repairing, RequirementStatus.Measuring -> {
                // view : 고객 요청 내용, 나의 제안 내용, 이전 실측 내용
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REQUIREMENT_TYPE,
                )
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = ESTIMATION_TYPE,
                )
                requirement.measurement?.let {
                    IconLabelContainer.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = PREVIOUS_ESTIMATION_TYPE,
                    )
                }
            }

            is RequirementStatus.Done -> {
                // view : 나의 최종 시공 내용, 고객 요청 내용, 이전 실측 내용(있으면)
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REPAIR_TYPE,
                )
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REQUIREMENT_TYPE,
                )
                requirement.measurement?.let {
                    IconLabelContainer.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = PREVIOUS_ESTIMATION_TYPE,
                    )
                }
                binding.requirementBasic.buttonCallToCustomerVisibility = false
            }

            is RequirementStatus.Closed -> {
                // view : 고객 리뷰, 나의 최종 시공 내용, 고객 요청 내용, 이전 실측 내용(있으면)
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REVIEW_TYPE,
                )
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REPAIR_TYPE,
                )
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REQUIREMENT_TYPE,
                )
                requirement.measurement?.let {
                    IconLabelContainer.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = PREVIOUS_ESTIMATION_TYPE,
                    )
                }
                binding.requirementBasic.buttonCallToCustomerVisibility = false
            }

            is RequirementStatus.Canceled -> {
                // view : 시공 취소 사유, 고객 요청 내용, 이전 실측 내용(있으면)
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = CANCEL_TYPE,
                )
                IconLabelContainer.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = REQUIREMENT_TYPE,
                )
                requirement.measurement?.let {
                    IconLabelContainer.addIconLabelContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = PREVIOUS_ESTIMATION_TYPE,
                    )
                }
                binding.requirementBasic.buttonCallToCustomerVisibility = false
            }
        }
    }
}