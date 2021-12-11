package kr.co.soogong.master.data.common

enum class CodeTable(
    val code: String,
    val inKorean: String,
    val asValue: Any,
) {
    PORTFOLIO("Portfolio", "포트폴리오", ""),
    PRICE_BY_PROJECT("Price", "시공 종류별 가격", ""),
    FLEXIBLE_COST("FlexibleCost", "현장 가격 변동 요인", ""),
    TRAVEL_COST("TravelCost", "출장비", ""),
    CRANE_USAGE("CraneUsage", "크레인 사용", ""),
    PACKAGE_COST("PackageCost", "짐 정리 비용", ""),
    OTHER_INFO("OtherInfo", "기타 변동 가능 사항", ""),
    OTHER_FLEXIBLE_OPTION("OtherFlexibleOption", "기타 변동 가능사항", ""),
    MASK("Mask", "마스크 착용", ""),
    OVERSHOES("Overshoes", "덧신 착용", ""),
    DISPOSAL("Disposal", "시공 쓰레기 처리", ""),
    ELEVATOR_PROTECTION("ElevatorProtection", "엘리베이터 보양작업", ""),
    IN_TIME("Intime", "약속시간 준수", ""),
    WARRANTY("A/S", "마감불량 시 재시공", ""),
    NOISE("Noise", "시끄러울 수 있음", ""),
    FREE("Free", "마스터", ""),
    GUARANTEE("Guarantee", "개런티 마스터", ""),
    NOT_APPROVED("NotApproved", "미승인", ""),
    REQUEST_APPROVE("RequestApprove", "승인대기", ""),
    APPROVED("Approved", "승인", ""),
    SOLE("Sole", "개인사업자", ""),
    CORPORATE("Corporate", "법인사업자", ""),
    FREELANCER("Freelancer", "프리랜서", ""),
    COMPARE("Compare", "비교견적", ""),
    SECRETARY("Secretary", "수공비서", ""),
    POSSIBLE("Possible", "가능", true),
    IMPOSSIBLE("impossible", "불가능", false);


    companion object {
        fun getCodeTableByCode(keyword: String): CodeTable? = values().find {
            it.code == keyword
        }

        fun getCodeTableByKorean(keyword: String): CodeTable? = values().find {
            it.inKorean == keyword
        }
    }
}


