package kr.co.soogong.master.domain.entity.requirement

import kr.co.soogong.master.data.entity.requirement.IRequirementDto
import kr.co.soogong.master.domain.entity.common.LabelTheme

sealed class RequirementStatus {
    abstract val asInt: Int
    abstract val code: String
    abstract val inKorean: String
    abstract val theme: LabelTheme
    abstract val guideLine: String

    object RequestMeasure : RequirementStatus() {
        override val inKorean = "방문요청"
        override val code = "RequestMeasure"
        override val asInt = 100
        override val theme = LabelTheme.BASIC_RED
        override val guideLine = "⚡️ 전화를 통해 방문일을 잡아주세요."
    }

    object Measuring : RequirementStatus() {
        override val inKorean = "방문예정"
        override val code = "Measuring"
        override val asInt = 101
        override val theme = LabelTheme.BASIC_BLUE
        override val guideLine = "\uD83D\uDCA1 방문일을 넣어주세요."
    }

    object Repairing : RequirementStatus() {
        override val inKorean = "시공예정"
        override val code = "Repairing"
        override val asInt = 200
        override val theme = LabelTheme.BASIC_BLUE
        override val guideLine = "\uD83D\uDEE0 시공 예정인 견적입니다."
    }

    object Done : RequirementStatus() {
        override val inKorean = "시공완료"
        override val code = "Done"
        override val asInt = 300
        override val theme = LabelTheme.BASIC_GREY
        override val guideLine = "✔️ 고객님에게 리뷰요청을 해주세요."
    }

    object Closed : RequirementStatus() {
        override val inKorean = "시공완료"
        override val code = "Closed"
        override val asInt = 301
        override val theme = LabelTheme.BASIC_GREY
        override val guideLine = "️\uD83D\uDCDD 고객님이 리뷰를 남겼습니다."
    }

    object Canceled : RequirementStatus() {
        override val inKorean = "시공취소"
        override val code = "Canceled"
        override val asInt = 400
        override val theme = LabelTheme.BASIC_GREY
        override val guideLine = "✔️ 취소된 문의입니다."
    }

    companion object {
        fun getStatusFromIRequirementDto(iRequirementDto: IRequirementDto): RequirementStatus {
            return when (iRequirementDto.statusCode) {
                RequestMeasure.code -> RequestMeasure
                Measuring.code -> Measuring
                Repairing.code -> Repairing
                Done.code -> Done
                Closed.code -> Closed
                else -> Canceled
            }
        }
    }
}