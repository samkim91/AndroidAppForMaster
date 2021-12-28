package kr.co.soogong.master.ui.requirement.action.view

import android.content.Context
import kr.co.soogong.master.atomic.molecules.Title3Container
import kr.co.soogong.master.atomic.molecules.Title3Container.Companion.CANCEL_TYPE
import kr.co.soogong.master.atomic.molecules.Title3Container.Companion.ESTIMATION_TYPE
import kr.co.soogong.master.atomic.molecules.Title3Container.Companion.PREVIOUS_ESTIMATION_TYPE
import kr.co.soogong.master.atomic.molecules.Title3Container.Companion.REPAIR_TYPE
import kr.co.soogong.master.atomic.molecules.Title3Container.Companion.REQUIREMENT_TYPE
import kr.co.soogong.master.atomic.molecules.Title3Container.Companion.REVIEW_TYPE
import kr.co.soogong.master.data.model.requirement.Requirement
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
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
            is RequirementStatus.Requested, RequirementStatus.RequestMeasure -> {
                // view : 고객 요청 내용, 이전 실측 내용(있으면)
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

            is RequirementStatus.RequestConsult -> {
                (requirement.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED).let { accepted ->
                    if (accepted) {
                        // view : 나의 제안 내용
                        Title3Container.addIconLabelContainer(
                            context = context,
                            container = flexibleContainer,
                            requirement = requirement,
                            contentType = ESTIMATION_TYPE,
                        )
                    }
                    // view : 고객 요청 내용, 이전 실측 내용(있으면)
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

            is RequirementStatus.Estimated, RequirementStatus.Measured -> {
                // view : 나의 제안 내용, 고객 요청 내용, 이전 실측 내용
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

            is RequirementStatus.Repairing, RequirementStatus.Measuring -> {
                // view : 고객 요청 내용, 나의 제안 내용, 이전 실측 내용
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = REQUIREMENT_TYPE,
                )
                Title3Container.addIconLabelContainer(
                    context = context,
                    container = flexibleContainer,
                    requirement = requirement,
                    contentType = ESTIMATION_TYPE,
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

            is RequirementStatus.Done -> {
                // view : 나의 최종 시공 내용, 고객 요청 내용, 이전 실측 내용(있으면)
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
                binding.requirementBasic.buttonCallToCustomerVisibility = false
            }

            is RequirementStatus.Closed -> {
                // view : 고객 리뷰, 나의 최종 시공 내용, 고객 요청 내용, 이전 실측 내용(있으면)
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
                binding.requirementBasic.buttonCallToCustomerVisibility = false
            }

            is RequirementStatus.Canceled -> {
                // view : 시공 취소 사유, 고객 요청 내용, 이전 실측 내용(있으면)
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
                binding.requirementBasic.buttonCallToCustomerVisibility = false
            }
        }
    }
}