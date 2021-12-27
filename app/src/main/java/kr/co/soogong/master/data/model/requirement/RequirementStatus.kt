package kr.co.soogong.master.data.model.requirement

import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode

sealed class RequirementStatus {
    abstract val asInt: Int
    abstract val code: String
    abstract val introductionText: String
    abstract val inKorean: String

    // region : Before progress tab
    object Requested : RequirementStatus() {
        override val inKorean = "견적요청"
        override val code = "Requested"
        override val asInt = 101
        override val introductionText = "견적서를 작성해주세요"
    }

    object RequestConsult : RequirementStatus() {
        override val inKorean = "상담요청"
        override val code = "RequestConsulting"
        override val asInt = 102
        override val introductionText = "고객에게 연락해주세요"
    }

    object RequestMeasure : RequirementStatus() {
        override val inKorean = "실측요청"
        override val code = "RequestMeasure"
        override val asInt = 103
        override val introductionText = ""
    }

    object Measuring : RequirementStatus() {
        override val inKorean = "실측예정"
        override val code = "Measuring"
        override val asInt = 104
        override val introductionText = "필요시 고객과 전화로 상담해주세요"
    }

    object Measured : RequirementStatus() {
        override val inKorean = "실측완료"
        override val code = "Measured"
        override val asInt = 105
        override val introductionText = "고객의 선택을 기다려주세요"
    }

    object Estimated : RequirementStatus() {
        override val inKorean = "매칭대기"
        override val code = "Estimated"
        override val asInt = 106
        override val introductionText = "고객의 선택을 기다려주세요"
    }
    // end region : Before progress tab

    // region : In progress tab
    object Repairing : RequirementStatus() {
        override val inKorean = "시공예정"
        override val code = "Repairing"
        override val asInt = 201
        override val introductionText = ""
    }

    object Done : RequirementStatus() {
        override val inKorean = "시공완료"
        override val code = "Done"
        override val asInt = 202
        override val introductionText = "고객에게 리뷰요청을 해주세요"
    }
    // end region : In progress tab

    // region : Done tab
    object Closed : RequirementStatus() {
        override val inKorean = "평가완료"
        override val code = "Closed"
        override val asInt = 301
        override val introductionText = "고객이 리뷰를 남겼어요"
    }

    object Canceled : RequirementStatus() {
        override val inKorean = "시공취소"
        override val code = "Canceled"
        override val asInt = 302
        override val introductionText = ""
    }
    // end region : Done tab

    companion object {
        fun getStatusFromRequirement(requirement: RequirementDto?): RequirementStatus {
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

        fun getStatusFromString(statusString: String): RequirementStatus = when (statusString) {
            Requested.code -> Requested
            RequestMeasure.code -> RequestMeasure
            Measuring.code -> Measuring
            Measured.code -> Measured
            Estimated.code -> Estimated
            Repairing.code -> Repairing
            Done.code -> Done
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
                    2 -> RequestMeasure.code
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