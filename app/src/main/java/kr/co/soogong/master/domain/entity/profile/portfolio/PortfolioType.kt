package kr.co.soogong.master.domain.entity.profile.portfolio

enum class PortfolioType(
    override val code: String,
    override val inKorean: String,
    val subheadline: String,
    val hint: String,
) : ICodeTable {

    PORTFOLIO("Portfolio",
        "포트폴리오",
        "마스터님의 포트폴리오를 작성해주세요.",
        "포트폴리오 정보가 많을수록, 더많이 노출되고, 고객님이 선택하는데 더 좋은 기준이 됩니다."),
    REPAIR_PHOTO("Photo", "시공 사진", "시공 사진을 등록해주세요.", "시공 사진이 많을수록 고객님이 시공을 결정하는데 더 많은 도움이 됩니다."),
    PRICE_BY_PROJECT("Price", "시공 종류별 가격", "시공 종류별 가격을 입력해주세요.", "다양한 시공을 입력해 주실수록 선택률이 상승합니다.")
    ;

    companion object {
        fun getPortfolioTypeFromCode(code: String): PortfolioType {
            return when (code) {
                PORTFOLIO.code -> PORTFOLIO
                REPAIR_PHOTO.code -> REPAIR_PHOTO
                PRICE_BY_PROJECT.code -> PRICE_BY_PROJECT
                else -> PORTFOLIO
            }
        }
    }
}
