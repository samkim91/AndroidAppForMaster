package kr.co.soogong.master.data.model.requirement

import kr.co.soogong.master.data.common.ColorTheme
import kr.co.soogong.master.data.dto.requirement.RequirementCardDto
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode

sealed class RequirementStatus {
    abstract val asInt: Int
    abstract val code: String
    abstract val inKorean: String
    abstract val theme: ColorTheme

    // region : Before progress tab
    object Requested : RequirementStatus() {
        override val inKorean = "견적요청"
        override val code = "Requested"
        override val asInt = 101
        override val theme = ColorTheme.Blue
    }

    object RequestConsult : RequirementStatus() {
        override val inKorean = "상담요청"
        override val code = "RequestConsulting"
        override val asInt = 102
        override val theme = ColorTheme.Blue
    }

    object RequestMeasure : RequirementStatus() {
        override val inKorean = "실측요청"
        override val code = "RequestMeasure"
        override val asInt = 103
        override val theme = ColorTheme.Blue
    }

    object Measuring : RequirementStatus() {
        override val inKorean = "실측예정"
        override val code = "Measuring"
        override val asInt = 104
        override val theme = ColorTheme.Blue
    }

    object Measured : RequirementStatus() {
        override val inKorean = "실측완료"
        override val code = "Measured"
        override val asInt = 105
        override val theme = ColorTheme.Grey
    }

    object Estimated : RequirementStatus() {
        override val inKorean = "매칭대기"
        override val code = "Estimated"
        override val asInt = 106
        override val theme = ColorTheme.Grey
    }
    // end region : Before progress tab

    // region : In progress tab
    object Repairing : RequirementStatus() {
        override val inKorean = "시공예정"
        override val code = "Repairing"
        override val asInt = 201
        override val theme = ColorTheme.Blue
    }

    object Done : RequirementStatus() {
        override val inKorean = "시공완료"
        override val code = "Done"
        override val asInt = 202
        override val theme = ColorTheme.Grey
    }
    // end region : In progress tab

    // region : Done tab
    object Closed : RequirementStatus() {
        override val inKorean = "평가완료"
        override val code = "Closed"
        override val asInt = 301
        override val theme = ColorTheme.Grey
    }

    object Canceled : RequirementStatus() {
        override val inKorean = "시공취소"
        override val code = "Canceled"
        override val asInt = 302
        override val theme = ColorTheme.Grey
    }
    // end region : Done tab

    companion object {
        fun getStatusFromRequirementDto(requirement: RequirementDto?): RequirementStatus {
            return when (requirement?.status) {
                // 진행 전
                Requested.code -> {
                    if (requirement.estimationDto?.requestConsultingYn == true) RequestConsult
                    else Requested
                }
                RequestMeasure.code -> RequestMeasure
                Measuring.code -> Measuring
                Measured.code -> Measured
                Estimated.code -> {
                    when {
                        requirement.estimationDto?.requestConsultingYn == true -> RequestConsult
                        requirement.estimationDto?.masterResponseCode == EstimationResponseCode.DEFAULT -> Requested
                        else -> Estimated
                    }
                }
                // 진행 중
                Repairing.code -> Repairing
                Done.code -> Done
                // 완료취소 탭
                Closed.code -> Closed
                Canceled.code -> Canceled
                else -> Canceled
            }
        }

        fun getStatusFromRequirementCardDto(requirementCardDto: RequirementCardDto) =
            when (requirementCardDto.status) {
                // 진행 전
                Requested.code -> {
                    if (requirementCardDto.requestConsultingYn) RequestConsult
                    else Requested
                }
                RequestMeasure.code -> RequestMeasure
                Measuring.code -> Measuring
                Measured.code -> Measured
                Estimated.code -> {
                    when {
                        requirementCardDto.masterResponseCode == EstimationResponseCode.DEFAULT -> Requested
                        requirementCardDto.requestConsultingYn -> RequestConsult
                        else -> Estimated
                    }
                }
                // 진행 중
                Repairing.code -> Repairing
                Done.code -> Done
                // 완료취소 탭
                Closed.code -> Closed
                else -> Canceled
            }

        fun getRequirementStatusFromTabIndex(
            mainTabIndex: Int?,
            filterTabIndex: Int?,
        ): String {
            if (mainTabIndex == null || filterTabIndex == null) return "All"

            return if (mainTabIndex == 0) {
                when (filterTabIndex) {
                    0 -> "BeforeProcess"
                    1 -> Requested.code
                    2 -> "Measure"
                    3 -> RequestConsult.code
                    4 -> Estimated.code
                    else -> ""
                }
            } else {
                when (filterTabIndex) {
                    0 -> "Processing"
                    1 -> Repairing.code
                    2 -> Done.code
                    else -> ""
                }
            }
        }
    }
}