package kr.co.soogong.master.data.model.requirement

import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode

sealed class RequirementStatus {
    abstract val asInt: Int
    abstract val code: String
    abstract val introductionText: String
    abstract val inKorean: String

    companion object {
        fun getStatusFromRequirement(requirement: RequirementDto?): RequirementStatus {
            if (requirement?.canceledYn == true) return Canceled

            return when (requirement?.status) {
                // 문의 탭
                Requested.code -> {
                    when {
                        requirement.estimationDto?.requestConsultingYn != true -> Requested
                        else -> RequestConsult
                    }
                }
                Estimated.code -> {
                    when {
                        requirement.estimationDto?.requestConsultingYn == true -> RequestConsult
                        requirement.estimationDto?.masterResponseCode == EstimationResponseCode.DEFAULT -> Requested
                        else -> Estimated
                    }
                }
                // 진행중 탭
                Measuring.code -> Measuring
                Measured.code -> Measured
                Repairing.code -> Repairing
                RequestFinish.code -> RequestFinish
                // 완료취소 탭
                Done.code -> Done
                Closed.code -> Closed
                Impossible.code -> Impossible
                // 실측 요청
                RequestMeasure.code -> RequestMeasure
                else -> Failed
            }
        }
    }
}

// region : Received Tab
object Requested : RequirementStatus() {
    override val inKorean = "견적요청"
    override val code = "Requested"
    override val asInt = 100
    override val introductionText = "견적서를 작성해주세요"
}

object Estimated : RequirementStatus() {
    override val inKorean = "매칭대기"
    override val code = "Estimated"
    override val asInt = 101
    override val introductionText = "고객의 선택을 기다려주세요"
}

object RequestConsult : RequirementStatus() {
    override val inKorean = "상담요청"
    override val code = "RequestConsult"
    override val asInt = 102
    override val introductionText = "견적서를 작성해주세요"
}
// end region : Received Tab

// region : Progress Tab
object RequestMeasure : RequirementStatus() {
    override val inKorean = "실측요청"
    override val code = "RequestMeasure"
    override val asInt = 200
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

object Repairing : RequirementStatus() {
    override val inKorean = "시공예정"
    override val code = "Repairing"
    override val asInt = 203
    override val introductionText = ""
}

object RequestFinish : RequirementStatus() {
    override val inKorean = "고객완료 요청"
    override val code = "RequestFinish"
    override val asInt = 204
    override val introductionText = "수리를 완료하고 리뷰를 쌓아보세요"
}
// end region : Progress Tab

// region : Done Tab
object Done : RequirementStatus() {
    override val inKorean = "시공완료"
    override val code = "Done"
    override val asInt = 300
    override val introductionText = "고객에게 리뷰요청을 해주세요"
}

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

object Impossible : RequirementStatus() {
    override val inKorean = "견적불가"
    override val code = "Impossible"
    override val asInt = 303
    override val introductionText = ""
}

object Failed : RequirementStatus() {
    override val inKorean = "매칭실패"
    override val code = "Failed"
    override val asInt = 304
    override val introductionText = ""
}
// end region : Done Tab