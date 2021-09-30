package kr.co.soogong.master.data.model.profile

sealed class CodeTable {
    abstract val code: String
    abstract val inKorean: String

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
                else -> PortfolioCodeTable
            }

        fun findBusinessTypeByKorean(string: String): CodeTable =
            when (string) {
                SoleCodeTable.inKorean -> SoleCodeTable
                CorporateCodeTable.inKorean -> CorporateCodeTable
                else -> FreelancerCodeTable
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

object PortfolioCodeTable : CodeTable() {
    override val code: String = "Portfolio"
    override val inKorean: String = "포트폴리오"
}

object PriceByProjectCodeTable : CodeTable() {
    override val code: String = "Price"
    override val inKorean: String = "시공 종류별 가격"
}

object FlexibleCostCodeTable : CodeTable() {
    override val code: String = "FlexibleCost"
    override val inKorean: String = "현장 가격 변동 요인"
}

object TravelCostCodeTable : CodeTable() {
    override val code: String = "TravelCost"
    override val inKorean: String = "출장비"
}

object CraneUsageCodeTable : CodeTable() {
    override val code: String = "CraneUsage"
    override val inKorean: String = "크레인 사용"
}

object PackageCostCodeTable : CodeTable() {
    override val code: String = "PackageCost"
    override val inKorean: String = "짐 정리 비용"
}

object OtherInfoCodeTable : CodeTable() {
    override val code: String = "OtherInfo"
    override val inKorean: String = "기타 변동 가능 사항"
}

object OtherFlexibleOptionCodeTable : CodeTable() {
    override val code: String = "OtherFlexibleOption"
    override val inKorean: String = "기타 변동 가능사항"
}

object MaskCodeTable : CodeTable() {
    override val code: String = "Mask"
    override val inKorean: String = "마스크 착용"
}

object OvershoesCodeTable : CodeTable() {
    override val code: String = "Overshoes"
    override val inKorean: String = "덧신 착용"
}

object DisposalCodeTable : CodeTable() {
    override val code: String = "Disposal"
    override val inKorean: String = "시공 쓰레기 처리"
}

object ElevatorProtectionCodeTable : CodeTable() {
    override val code: String = "ElevatorProtection"
    override val inKorean: String = "엘리베이터 보양작업"
}

object IntimeCodeTable : CodeTable() {
    override val code: String = "Intime"
    override val inKorean: String = "약속시간 준수"
}

object AsCodeTable : CodeTable() {
    override val code: String = "A/S"
    override val inKorean: String = "마감불량 시 재시공"
}

object NoiseCodeTable : CodeTable() {
    override val code: String = "Noise"
    override val inKorean: String = "시끄러울 수 있음"
}

object FreeCodeTable : CodeTable() {
    override val code: String = "Free"
    override val inKorean: String = "마스터"
}

object GuaranteeCodeTable : CodeTable() {
    override val code: String = "Guarantee"
    override val inKorean: String = "개런티 마스터"
}

object NotApprovedCodeTable : CodeTable() {
    override val code: String = "NotApproved"
    override val inKorean: String = "미승인"
}

object RequestApproveCodeTable : CodeTable() {
    override val code: String = "RequestApprove"
    override val inKorean: String = "승인 대기"
}

object ApprovedCodeTable : CodeTable() {
    override val code: String = "Approved"
    override val inKorean: String = "승인"
}

object SoleCodeTable : CodeTable() {
    override val code: String = "Sole"
    override val inKorean: String = "개인사업자"
}

object CorporateCodeTable : CodeTable() {
    override val code: String = "Corporate"
    override val inKorean: String = "법인사업자"
}

object FreelancerCodeTable : CodeTable() {
    override val code: String = "Freelancer"
    override val inKorean: String = "프리랜서"
}

object CompareCodeTable : CodeTable() {
    override val code: String = "Compare"
    override val inKorean: String = "비교견적"
}

object SecretaryCodeTable : CodeTable() {
    override val code: String = "Secretary"
    override val inKorean: String = "수공비서"
}


