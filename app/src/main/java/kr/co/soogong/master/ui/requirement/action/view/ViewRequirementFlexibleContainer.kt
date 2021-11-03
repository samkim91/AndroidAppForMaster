package kr.co.soogong.master.ui.requirement.action.view

import android.content.Context
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer
import kr.co.soogong.master.utility.extension.formatDateWithoutDay
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
        actionBar.title.text = requirement.address

        when (RequirementStatus.getStatusFromRequirement(requirement)) {
            is RequirementStatus.RequestMeasure -> {
                actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = false
                // view : 고객 요청 내용(spread), 이전 실측 내용(있으면, spread)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = true,
                    includingCancel = false
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                }
            }

            is RequirementStatus.Requested, RequirementStatus.RequestConsult -> {
                actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                (requirement.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED).let { accepted ->
                    if (accepted) {
                        // view : 나의 제안 내용(spread), 고객 요청 내용, 이전 실측 내용(있으면)
                        RequirementDrawerContainer.addDrawerContainer(
                            context = context,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = RequirementDrawerContainer.ESTIMATION_TYPE,
                            isSpread = true,
                            includingCancel = false
                        )
                        RequirementDrawerContainer.addDrawerContainer(
                            context = context,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                            isSpread = false,
                            includingCancel = false
                        )
                        requirement.measurement?.let {
                            RequirementDrawerContainer.addDrawerContainer(
                                context = context,
                                container = flexibleContainer,
                                requirementDto = requirement,
                                contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                                isSpread = false,
                                includingCancel = false
                            )
                        }
                        return@let
                    }
                    // view : 고객 요청 내용(spread), 이전 실측 내용(있으면, spread)
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = context,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                            isSpread = true,
                            includingCancel = false
                        )
                    }
                }
            }

            is RequirementStatus.Estimated -> {
                actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                // view : 나의 제안 내용(spread), 고객 요청 내용, 이전 실측 내용(있으면)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.ESTIMATION_TYPE,
                    isSpread = true,
                    includingCancel = false
                )
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = false,
                    includingCancel = false
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }
            }

            is RequirementStatus.Repairing -> {
                // view : 고객 요청 내용(spread, includingCancel), 나의 제안 내용, 이전 실측 내용(있으면)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = true,
                    includingCancel = true
                )
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.ESTIMATION_TYPE,
                    isSpread = false,
                    includingCancel = false
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }
            }

            is RequirementStatus.RequestFinish -> {
                // view : 고객 요청 내용(spread), 나의 제안 내용, 이전 실측 내용(있으면)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = true,
                    includingCancel = false
                )
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.ESTIMATION_TYPE,
                    isSpread = false,
                    includingCancel = false
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }
            }

            is RequirementStatus.Measuring -> {
                actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                // view : 고객요청(spread, includingCancel), 이전 실측 내용(있으면, spread)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = true,
                    includingCancel = true
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                }
            }

            is RequirementStatus.Measured -> {
                actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                // view : 나의 실측 내용(spread, includingCancel), 고객요청, 이전 실측 내용(있으면)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.ESTIMATION_TYPE,
                    isSpread = true,
                    includingCancel = true
                )
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = false,
                    includingCancel = false
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }
            }

            is RequirementStatus.Done -> {
                // view : 나의 최종 시공 내용(spread), 고객 요청 내용, 이전 실측 내용(있으면)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REPAIR_TYPE,
                    isSpread = true,
                    includingCancel = false
                )
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = false,
                    includingCancel = false
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }
                // 시공완료일
                requirementStatus.endingText = context.getString(
                    R.string.progress_ending_text_with_date,
                    requirement.estimationDto?.repair?.actualDate.formatDateWithoutDay()
                )
                requirementStatus.endingTextColor = context.getColor(R.color.color_0C5E47)
            }

            is RequirementStatus.Closed -> {
                // view : 고객 리뷰(spread), 나의 최종 시공 내용, 고객 요청 내용, 이전 실측 내용(있으면)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REVIEW_TYPE,
                    isSpread = true,
                    includingCancel = false
                )
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REPAIR_TYPE,
                    isSpread = false,
                    includingCancel = false
                )
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = false,
                    includingCancel = false
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }
                // 시공완료일
                requirementStatus.endingText = context.getString(
                    R.string.progress_ending_text_with_date,
                    requirement.estimationDto?.repair?.actualDate.formatDateWithoutDay()
                )
                requirementStatus.endingTextColor = context.getColor(R.color.color_0C5E47)
            }

            is RequirementStatus.Canceled -> {
                // view : 시공 취소 사유, 고객 요청 내용, 이전 실측 내용(있으면)
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.CANCEL_TYPE,
                    isSpread = true,
                    includingCancel = false
                )
                RequirementDrawerContainer.addDrawerContainer(
                    context = context,
                    container = flexibleContainer,
                    requirementDto = requirement,
                    contentType = RequirementDrawerContainer.REQUIREMENT_TYPE,
                    isSpread = false,
                    includingCancel = false
                )
                requirement.measurement?.let {
                    RequirementDrawerContainer.addDrawerContainer(
                        context = context,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = RequirementDrawerContainer.PREVIOUS_ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }
                requirementStatus.isVisible = false
            }
        }
    }
}