package kr.co.soogong.master.data.model.profile

sealed class CodeTable {
    abstract val code: String
    abstract val inKorean: String
    abstract val asValue: Any

    companion object {
        fun findCodeTableByKorean(string: String): CodeTable =
            when (string) {
                PortfolioCodeTable.inKorean -> PortfolioCodeTable
                PriceByProjectCodeTable.inKorean -> PriceByProjectCodeTable
                FlexibleCostCodeTable.inKorean -> FlexibleCostCodeTable
                TravelCostCodeTable.inKorean -> TravelCostCodeTable
                CraneUsageCodeTable.inKorean -> CraneUsageCodeTable
                PackageCostCodeTable.inKorean -> PackageCostCodeTable
                OtherInfoCodeTable.inKorean -> OtherInfoCodeTable
                OtherFlexibleOptionCodeTable.inKorean -> OtherFlexibleOptionCodeTable
                MaskCodeTable.inKorean -> MaskCodeTable
                OvershoesCodeTable.inKorean -> OvershoesCodeTable
                DisposalCodeTable.inKorean -> DisposalCodeTable
                ElevatorProtectionCodeTable.inKorean -> ElevatorProtectionCodeTable
                IntimeCodeTable.inKorean -> IntimeCodeTable
                NoiseCodeTable.inKorean -> NoiseCodeTable
                AsCodeTable.inKorean -> AsCodeTable
                SoleCodeTable.inKorean -> SoleCodeTable
                CorporateCodeTable.inKorean -> CorporateCodeTable
                FreelancerCodeTable.inKorean -> FreelancerCodeTable
                CompareCodeTable.inKorean -> CompareCodeTable
                SecretaryCodeTable.inKorean -> SecretaryCodeTable
                PossibleCodeTable.inKorean -> PossibleCodeTable
                ImpossibleCodeTable.inKorean -> ImpossibleCodeTable
                else -> PortfolioCodeTable
            }

        fun findBusinessTypeByCode(code: String): CodeTable =
            when (code) {
                SoleCodeTable.code -> SoleCodeTable
                CorporateCodeTable.code -> CorporateCodeTable
                else -> FreelancerCodeTable
            }

        fun findSubscriptionPlanByCode(code: String): CodeTable =
            when (code) {
                GuaranteeCodeTable.code -> GuaranteeCodeTable
                else -> FreeCodeTable
            }

        fun isSecretaryRequirement(code: String?): Boolean = code == SecretaryCodeTable.code
    }
}

object PossibleCodeTable : CodeTable() {
    override val code: String = "Possible"
    override val inKorean: String = "가능"
    override val asValue: Boolean = true
}

object ImpossibleCodeTable : CodeTable() {
    override val code: String = "Impossible"
    override val inKorean: String = "불가능"
    override val asValue: Boolean = false
}

object PortfolioCodeTable : CodeTable() {
    override val code: String = "Portfolio"
    override val inKorean: String = "포트폴리오"
    override val asValue: Any = ""
}

object PriceByProjectCodeTable : CodeTable() {
    override val code: String = "Price"
    override val inKorean: String = "시공 종류별 가격"
    override val asValue: Any = ""
}

object FlexibleCostCodeTable : CodeTable() {
    override val code: String = "FlexibleCost"
    override val inKorean: String = "현장 가격 변동 요인"
    override val asValue: Any = ""
}

object TravelCostCodeTable : CodeTable() {
    override val code: String = "TravelCost"
    override val inKorean: String = "출장비"
    override val asValue: Any = ""
}

object CraneUsageCodeTable : CodeTable() {
    override val code: String = "CraneUsage"
    override val inKorean: String = "크레인 사용"
    override val asValue: Any = ""
}

object PackageCostCodeTable : CodeTable() {
    override val code: String = "PackageCost"
    override val inKorean: String = "짐 정리 비용"
    override val asValue: Any = ""
}

object OtherInfoCodeTable : CodeTable() {
    override val code: String = "OtherInfo"
    override val inKorean: String = "기타 변동 가능 사항"
    override val asValue: Any = ""
}

object OtherFlexibleOptionCodeTable : CodeTable() {
    override val code: String = "OtherFlexibleOption"
    override val inKorean: String = "기타 변동 가능사항"
    override val asValue: Any = ""
}

object MaskCodeTable : CodeTable() {
    override val code: String = "Mask"
    override val inKorean: String = "마스크 착용"
    override val asValue: Any = ""
}

object OvershoesCodeTable : CodeTable() {
    override val code: String = "Overshoes"
    override val inKorean: String = "덧신 착용"
    override val asValue: Any = ""
}

object DisposalCodeTable : CodeTable() {
    override val code: String = "Disposal"
    override val inKorean: String = "시공 쓰레기 처리"
    override val asValue: Any = ""
}

object ElevatorProtectionCodeTable : CodeTable() {
    override val code: String = "ElevatorProtection"
    override val inKorean: String = "엘리베이터 보양작업"
    override val asValue: Any = ""
}

object IntimeCodeTable : CodeTable() {
    override val code: String = "Intime"
    override val inKorean: String = "약속시간 준수"
    override val asValue: Any = ""
}

object AsCodeTable : CodeTable() {
    override val code: String = "A/S"
    override val inKorean: String = "마감불량 시 재시공"
    override val asValue: Any = ""
}

object NoiseCodeTable : CodeTable() {
    override val code: String = "Noise"
    override val inKorean: String = "시끄러울 수 있음"
    override val asValue: Any = ""
}

object FreeCodeTable : CodeTable() {
    override val code: String = "Free"
    override val inKorean: String = "마스터"
    override val asValue: Any = ""
}

object GuaranteeCodeTable : CodeTable() {
    override val code: String = "Guarantee"
    override val inKorean: String = "개런티 마스터"
    override val asValue: Any = ""
}

object NotApprovedCodeTable : CodeTable() {
    override val code: String = "NotApproved"
    override val inKorean: String = "미승인"
    override val asValue: Any = ""
}

object RequestApproveCodeTable : CodeTable() {
    override val code: String = "RequestApprove"
    override val inKorean: String = "승인 대기"
    override val asValue: Any = ""
}

object ApprovedCodeTable : CodeTable() {
    override val code: String = "Approved"
    override val inKorean: String = "승인"
    override val asValue: Any = ""
}

object SoleCodeTable : CodeTable() {
    override val code: String = "Sole"
    override val inKorean: String = "개인사업자"
    override val asValue: Any = ""
}

object CorporateCodeTable : CodeTable() {
    override val code: String = "Corporate"
    override val inKorean: String = "법인사업자"
    override val asValue: Any = ""
}

object FreelancerCodeTable : CodeTable() {
    override val code: String = "Freelancer"
    override val inKorean: String = "프리랜서"
    override val asValue: Any = ""
}

object CompareCodeTable : CodeTable() {
    override val code: String = "Compare"
    override val inKorean: String = "비교견적"
    override val asValue: Any = ""
}

object SecretaryCodeTable : CodeTable() {
    override val code: String = "Secretary"
    override val inKorean: String = "수공비서"
    override val asValue: Any = ""
}


