package kr.co.soogong.master.data.model.requirement

import kr.co.soogong.master.data.dto.requirement.RequirementDto

sealed class RequirementStatus {
    abstract fun toInt(): Int
    abstract fun toCode(): String
    abstract fun getIntroductionText(): String

    object Requested : RequirementStatus() {
        override fun toString(): String = "견적요청"
        override fun toCode(): String = "Requested"
        override fun toInt(): Int = 100
        override fun getIntroductionText(): String = "견적서를 작성해주세요"
    }

    object Estimated : RequirementStatus() {
        override fun toString(): String = "매칭대기"
        override fun toCode(): String = "Estimated"
        override fun toInt(): Int = 101
        override fun getIntroductionText(): String = "고객의 선택을 기다려주세요"
    }

    object Repairing : RequirementStatus() {
        override fun toString(): String = "시공진행중"
        override fun toCode(): String = "Repairing"
        override fun toInt(): Int = 200
        override fun getIntroductionText(): String = ""
    }

    object RequestFinish : RequirementStatus() {
        override fun toString(): String = "고객완료 요청"
        override fun toCode(): String = "RequestFinish"
        override fun toInt(): Int = 201
        override fun getIntroductionText(): String = "수리를 완료하고 리뷰를 쌓아보세요"
    }

    object Done : RequirementStatus() {
        override fun toString(): String = "시공완료"
        override fun toCode(): String = "Done"
        override fun toInt(): Int = 300
        override fun getIntroductionText(): String = "고객에게 리뷰요청을 해주세요"
    }

    object Closed : RequirementStatus() {
        override fun toString(): String = "평가완료"
        override fun toCode(): String = "Closed"
        override fun toInt(): Int = 301
        override fun getIntroductionText(): String = "고객이 리뷰를 남겼어요"
    }

    object Canceled : RequirementStatus() {
        override fun toString(): String = "시공취소"
        override fun toCode(): String = "Canceled"
        override fun toInt(): Int = 302
        override fun getIntroductionText(): String = ""
    }

    object Impossible : RequirementStatus() {
        override fun toString(): String = "견적불가"
        override fun toCode(): String = "Impossible"
        override fun toInt(): Int = 303
        override fun getIntroductionText(): String = ""
    }

    object Failed : RequirementStatus() {
        override fun toString(): String = "매칭실패"
        override fun toCode(): String = "Failed"
        override fun toInt(): Int = 304
        override fun getIntroductionText(): String = ""
    }

    companion object {
        fun getStatus(requirement: RequirementDto?): RequirementStatus {
            if (requirement?.canceledYn == true) return Canceled

            return when (requirement?.status) {
                // 견적탭
                Requested.toCode() -> Requested
                Estimated.toCode() -> {
                    if (requirement.estimationDto?.masterResponseCode == "Default") Requested else Estimated
                }
                // 시공탭
                Repairing.toCode() -> Repairing
                RequestFinish.toCode() -> RequestFinish
                // 완료탭
                Done.toCode() -> Done
                Closed.toCode() -> Closed
                Impossible.toCode() -> Impossible
                else -> Failed
            }
        }

        fun getAllStatusCodes(): List<String>{
            return listOf(
                Requested.toCode(),
                Estimated.toCode(),
                Repairing.toCode(),
                RequestFinish.toCode(),
                Done.toCode(),
                Closed.toCode(),
                Canceled.toCode(),
                Impossible.toCode(),
            )
        }

        fun getReceivedCodes(): List<String>{
            return listOf(
                Requested.toCode(),
                Estimated.toCode(),
            )
        }

        fun getProgressCodes(): List<String>{
            return listOf(
                Repairing.toCode(),
                RequestFinish.toCode(),
            )
        }

        fun getDoneCodes(): List<String>{
            return listOf(
                Done.toCode(),
                Closed.toCode(),
                Canceled.toCode(),
                Impossible.toCode(),
            )
        }
    }
}