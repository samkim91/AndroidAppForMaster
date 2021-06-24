package kr.co.soogong.master.data.model.requirement.repair

sealed class RepairCanceledReason {
    abstract val code: String
    abstract val inKorean: String

    companion object {
        fun getCanceledReason(code: String?): RepairCanceledReason =
            when (code) {
                ChangeOfMind.code -> ChangeOfMind
                NoResponse.code -> NoResponse
                RepairImpossible.code -> RepairImpossible
                else -> DifferentCost
            }
    }
}

object ChangeOfMind : RepairCanceledReason() {
    override val code: String = "ChangeOfMind"
    override val inKorean: String = "고객의 단순 변심"
}

object NoResponse : RepairCanceledReason() {
    override val code: String = "NoResponse"
    override val inKorean: String = "고객의 연락 부재"
}

object RepairImpossible : RepairCanceledReason() {
    override val code: String = "RepairImpossible"
    override val inKorean: String = "현장 실측 결과 시공이 불가"
}

object DifferentCost : RepairCanceledReason() {
    override val code: String = "DifferentCost"
    override val inKorean: String = "견적가와 현장 실측 결과가 상이하여 고객이 취소"
}