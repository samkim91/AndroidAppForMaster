package kr.co.soogong.master.domain.entity.common

enum class CodeTable(
    val code: String,
    val inKorean: String,
    val asValue: Any,
) {
    // 공통
    POSSIBLE("Possible", "가능", true),
    IMPOSSIBLE("Impossible", "불가능", false),
    EXIST("Exist", "있음", true),
    NOT_EXIST("NotExist", "없음", false),

    // UI 를 위한 코드
    NOT_REQUIRED("NotRequired", "사용 안함", ""),
    OVER_TWO("OverTwo", "2층 이상", ""),
    NO_ELEVATOR("NoElevator", "엘리베이터 사용 불가시", ""),
    INTEGRATION("Integration", "통합 견적", ""),
    BY_ITEM("ByItem", "항목별 견적", ""),

    // 포트폴리오 및 시공종류별 가격
    PORTFOLIO("Portfolio", "포트폴리오", ""),
    REPAIR_PHOTO("Photo", "시공 사진", ""),
    PRICE_BY_PROJECT("Price", "시공 종류별 가격", ""),

    // 현장 가격 변동 요인
    FLEXIBLE_COST("FlexibleCost", "현장 가격 변동 요인", ""),
    TRAVEL_COST("TravelCost", "출장비", ""),
    CRANE_USAGE("CraneUsage", "크레인 사용", ""),
    PACKAGE_COST("PackageCost", "짐 정리 비용", ""),
    OTHER_INFO("OtherInfo", "기타 변동 가능 사항", ""),

    // 기타 변동 가능 사항
    OTHER_FLEXIBLE_OPTION("OtherFlexibleOption", "기타 변동 가능사항", ""),
    MASK("Mask", "마스크 착용", ""),
    OVERSHOES("Overshoes", "덧신 착용", ""),
    DISPOSAL("Disposal", "시공 쓰레기 처리", ""),
    ELEVATOR_PROTECTION("ElevatorProtection", "엘리베이터 보양작업", ""),
    IN_TIME("Intime", "약속시간 준수", ""),
    WARRANTY("DefectRework", "마감불량 시 재시공", ""),
    NOISE("Noise", "시끄러울 수 있음", ""),

    // 마스터 등급
    FREE("Free", "마스터", ""),
    GUARANTEE("Guarantee", "개런티 마스터", ""),
    ADVISOR("Advisor", "어드바이저", ""),

    // 마스터 승인 상태
    NOT_APPROVED("NotApproved", "미승인", ""),
    REQUEST_APPROVE("RequestApprove", "승인대기", ""),
    APPROVED("Approved", "승인", ""),

    // 사업자 종류
    SOLE("Sole", "개인사업자", ""),
    CORPORATE("Corporate", "법인사업자", ""),
    FREELANCER("Freelancer", "프리랜서", ""),

    // 문의 타입
    COMPARE("Compare", "비교견적", ""),
    SECRETARY("Secretary", "수공비서", ""),

    // 리뷰 스코어 타입
    RECOMMENDATION("Recommendation", "추천도", ""),
    KINDNESS("Kindness", "친절도", ""),
    QUALITY("Quality", "마감수준", ""),
    AFFORDABILITY("Affordability", "가격만족", ""),
    PUNCTUALITY("Punctuality", "기일준수", "")

    ;

    companion object {
        fun getCodeTableByCode(keyword: String): CodeTable? = values().find {
            it.code == keyword
        }

        fun getCodeTableByKorean(keyword: String): CodeTable? = values().find {
            it.inKorean == keyword
        }
    }
}


