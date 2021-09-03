package kr.co.soogong.master.data.model.requirement.repair

abstract class CanceledReason {
    abstract val code: String
    abstract val inKorean: String

    companion object {
        fun getCanceledReasonFromCode(code: String?): CanceledReason =
            when (code) {
                OutOfContactClient.code -> OutOfContactClient
                FailOfNegotiations.code -> FailOfNegotiations
                NotOnSchedule.code -> NotOnSchedule
                RefuseOfClient.code -> RefuseOfClient
                ImpossibleRepair.code -> ImpossibleRepair
                else -> EtcCanceledReason
            }
    }
}

object OutOfContactClient : CanceledReason() {
    override val code: String = "OutOfContact-Client"
    override val inKorean: String = "고객 연락 부재"
}

object FailOfNegotiations : CanceledReason() {
    override val code: String = "FailOfNegotiations"
    override val inKorean: String = "시공 단가 협상 결렬"
}

object NotOnSchedule : CanceledReason() {
    override val code: String = "NotOnSchedule"
    override val inKorean: String = "시공 일정이 안 맞음"
}

object RefuseOfClient : CanceledReason() {
    override val code: String = "RefuseOfClient"
    override val inKorean: String = "고객 단순 거절"
}

object ImpossibleRepair : CanceledReason() {
    override val code: String = "ImpossibleRepair"
    override val inKorean: String = "시공 불가"
}

object EtcCanceledReason : CanceledReason() {
    override val code: String = "EtcCanceledReason"
    override val inKorean: String = "기타(상세내용 참고)"
}