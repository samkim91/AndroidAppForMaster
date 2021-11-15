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
        override val asInt = 100
        override val introductionText = "견적서를 작성해주세요"
    }

    object RequestConsult : RequirementStatus() {
        override val inKorean = "상담요청"
        override val code = "RequestConsult"
        override val asInt = 101
        override val introductionText = "고객에게 연락해주세요"
    }

    object RequestMeasure : RequirementStatus() {
        override val inKorean = "실측요청"
        override val code = "RequestMeasure"
        override val asInt = 102
        override val introductionText = ""
    }

    object Measuring : RequirementStatus() {
        override val inKorean = "실측예정"
        override val code = "Measuring"
        override val asInt = 201
        override val introductionText = "필요시 고객과 전화로 상담해주세요"
    }

    object Measured : RequirementStatus() {
        override val inKorean = "실측완료"
        override val code = "Measured"
        override val asInt = 202
        override val introductionText = "고객의 선택을 기다려주세요"
    }

    object Estimated : RequirementStatus() {
        override val inKorean = "매칭대기"
        override val code = "Estimated"
        override val asInt = 103
        override val introductionText = "고객의 선택을 기다려주세요"
    }
    // end region : Before progress tab

    // region : In progress tab
    object Repairing : RequirementStatus() {
        override val inKorean = "시공예정"
        override val code = "Repairing"
        override val asInt = 203
        override val introductionText = ""
    }

    object Done : RequirementStatus() {
        override val inKorean = "시공완료"
        override val code = "Done"
        override val asInt = 300
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
                    if (requirement.estimationDto?.requestConsultingYn == true) RequestConsult else Requested
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

        fun getRequirementStatusFromTabIndex(
            mainTabIndex: Int?,
            filterTabIndex: Int?,
        ): List<String> {
            if (mainTabIndex == null || filterTabIndex == null) return emptyList()

            return if (mainTabIndex == 0) {
                when (filterTabIndex) {
                    0 -> listOf(Requested.code,
                        RequestMeasure.code,
                        Measuring.code,
                        Measured.code,
                        Estimated.code)
                    1, 3 -> listOf(Requested.code, Estimated.code)
                    2 -> listOf(RequestMeasure.code, Measuring.code, Measured.code)
                    4 -> listOf(Estimated.code)
                    else -> emptyList()
                }
            } else {
                when (filterTabIndex) {
                    0 -> listOf(Repairing.code, Done.code)
                    1 -> listOf(Repairing.code)
                    2 -> listOf(Done.code)
                    else -> emptyList()
                }
            }
        }

        val statusForNewRequirements = listOf(
            Requested,
            RequestConsult,
            RequestMeasure,
            Estimated,
            Measuring,
            Measured,
            Repairing,
            Done,
            Closed,
            Canceled,
        )
    }
}