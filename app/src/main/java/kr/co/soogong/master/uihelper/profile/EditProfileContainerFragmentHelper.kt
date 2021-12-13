package kr.co.soogong.master.uihelper.profile

import androidx.fragment.app.Fragment
import kr.co.soogong.master.ui.profile.detail.address.EditAddressFragment
import kr.co.soogong.master.ui.profile.detail.businessunitinformation.EditBusinessUnitInformationFragment
import kr.co.soogong.master.ui.profile.detail.email.EditEmailFragment
import kr.co.soogong.master.ui.profile.detail.freemeasure.EditFreeMeasureFragment
import kr.co.soogong.master.ui.profile.detail.introduction.EditIntroductionFragment
import kr.co.soogong.master.ui.profile.detail.major.EditMajorFragment
import kr.co.soogong.master.ui.profile.detail.masterconfig.EditMasterConfigFragment
import kr.co.soogong.master.ui.profile.detail.ownername.EditOwnerNameFragment
import kr.co.soogong.master.ui.profile.detail.phonenumber.EditPhoneNumberFragment
import kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio.PortfolioFragment
import kr.co.soogong.master.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectFragment
import kr.co.soogong.master.ui.profile.detail.shopimages.EditShopImagesFragment
import kr.co.soogong.master.ui.profile.detail.warranty.EditWarrantyInformationFragment

object EditProfileContainerFragmentHelper {
    const val TAG = "EditProfileContainerFragmentHelper"

    const val FREE_MEASURE = "무료 방문 실측 여부 수정하기"
    const val EDIT_PORTFOLIO = "포트폴리오 수정하기"
    const val ADD_PORTFOLIO = "포트폴리오 추가하기"
    const val EDIT_PRICE_BY_PROJECTS = "시공 종류별 가격 수정하기"
    const val ADD_PRICE_BY_PROJECTS = "시공 종류별 가격 추가하기"

    const val EDIT_EMAIL = "이메일 등록·수정하기"

    const val EDIT_INTRODUCTION = "업체 소개"
    const val EDIT_SHOP_IMAGES = "대표 이미지"
    const val EDIT_BUSINESS_UNIT_INFORMATION = "사업자 정보"
    const val EDIT_WARRANTY_INFORMATION = "A/S 정보"
    const val EDIT_OWNER_NAME = "대표자명 등록·수정하기"
    const val EDIT_PHONE_NUMBER = "휴대폰 번호"
    const val EDIT_ADDRESS = "활동 지역"
    const val EDIT_MAJOR = "시공 가능 업종"
    const val EDIT_MASTER_CONFIG = "기타 변동 요인"

    fun getFragment(pageName: String, itemId: Int?): Fragment =
        when (pageName) {
            FREE_MEASURE -> EditFreeMeasureFragment.newInstance()
            ADD_PORTFOLIO -> PortfolioFragment.newInstance(ADD_PORTFOLIO, null)
            EDIT_PORTFOLIO -> PortfolioFragment.newInstance(EDIT_PORTFOLIO, itemId)
            ADD_PRICE_BY_PROJECTS -> PriceByProjectFragment.newInstance(ADD_PRICE_BY_PROJECTS, null)
            EDIT_PRICE_BY_PROJECTS -> PriceByProjectFragment.newInstance(
                EDIT_PRICE_BY_PROJECTS,
                itemId
            )
            EDIT_MASTER_CONFIG -> EditMasterConfigFragment.newInstance()
            EDIT_EMAIL -> EditEmailFragment.newInstance()

            EDIT_INTRODUCTION -> EditIntroductionFragment.newInstance()
            EDIT_SHOP_IMAGES -> EditShopImagesFragment.newInstance()
            EDIT_BUSINESS_UNIT_INFORMATION -> EditBusinessUnitInformationFragment.newInstance()
            EDIT_WARRANTY_INFORMATION -> EditWarrantyInformationFragment.newInstance()
            EDIT_OWNER_NAME -> EditOwnerNameFragment.newInstance()
            EDIT_PHONE_NUMBER -> EditPhoneNumberFragment.newInstance()
            EDIT_MAJOR -> EditMajorFragment.newInstance()
            EDIT_ADDRESS -> EditAddressFragment.newInstance()
            else -> Fragment()
        }
}