package kr.co.soogong.master.presentation.uihelper.profile

import androidx.fragment.app.Fragment
import kr.co.soogong.master.domain.entity.profile.portfolio.IPortfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.Portfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.PriceByProject
import kr.co.soogong.master.domain.entity.profile.portfolio.RepairPhoto
import kr.co.soogong.master.presentation.ui.profile.detail.address.EditAddressFragment
import kr.co.soogong.master.presentation.ui.profile.detail.businessunitinformation.EditBusinessUnitInformationFragment
import kr.co.soogong.master.presentation.ui.profile.detail.email.EditEmailFragment
import kr.co.soogong.master.presentation.ui.profile.detail.freemeasure.EditFreeMeasureFragment
import kr.co.soogong.master.presentation.ui.profile.detail.introduction.EditIntroductionFragment
import kr.co.soogong.master.presentation.ui.profile.detail.major.EditMajorFragment
import kr.co.soogong.master.presentation.ui.profile.detail.masterconfig.EditMasterConfigFragment
import kr.co.soogong.master.presentation.ui.profile.detail.phonenumber.EditPhoneNumberFragment
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.portfolio.PortfolioFragment
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.pricebyproject.PriceByProjectFragment
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.repairphoto.RepairPhotoFragment
import kr.co.soogong.master.presentation.ui.profile.detail.shopimages.EditShopImagesFragment
import kr.co.soogong.master.presentation.ui.profile.detail.warranty.EditWarrantyInformationFragment

object EditProfileContainerFragmentHelper {
    const val TAG = "EditProfileContainerFragmentHelper"

    // 필수 정보
    const val EDIT_INTRODUCTION = "업체 소개"
    const val EDIT_SHOP_IMAGES = "대표 이미지"
    const val EDIT_BUSINESS_UNIT_INFORMATION = "사업자 정보"
    const val EDIT_WARRANTY_INFORMATION = "A/S 정보"
    const val EDIT_PHONE_NUMBER = "휴대폰 번호"
    const val EDIT_MAJOR = "시공 가능 업종"
    const val EDIT_ADDRESS = "활동 지역"

    // 선택 정보
    const val FREE_MEASURE = "무료 방문 실측 여부"
    const val ADD_PORTFOLIO = "포트폴리오 추가"
    const val EDIT_PORTFOLIO = "포트폴리오 수정"
    const val ADD_REPAIR_PHOTO = "시공 사진 추가"
    const val EDIT_REPAIR_PHOTO = "시공 사진 수정"
    const val ADD_PRICE_BY_PROJECTS = "시공 종류별 가격 추가"
    const val EDIT_PRICE_BY_PROJECTS = "시공 종류별 가격 수정"
    const val EDIT_MASTER_CONFIG = "기타 변동 요인"
    const val EDIT_EMAIL = "이메일"

    fun getFragment(pageName: String): Fragment =
        when (pageName) {
            FREE_MEASURE -> EditFreeMeasureFragment.newInstance()
            EDIT_MASTER_CONFIG -> EditMasterConfigFragment.newInstance()
            EDIT_EMAIL -> EditEmailFragment.newInstance()

            EDIT_INTRODUCTION -> EditIntroductionFragment.newInstance()
            EDIT_SHOP_IMAGES -> EditShopImagesFragment.newInstance()
            EDIT_BUSINESS_UNIT_INFORMATION -> EditBusinessUnitInformationFragment.newInstance()
            EDIT_WARRANTY_INFORMATION -> EditWarrantyInformationFragment.newInstance()
            EDIT_PHONE_NUMBER -> EditPhoneNumberFragment.newInstance()
            EDIT_MAJOR -> EditMajorFragment.newInstance()
            EDIT_ADDRESS -> EditAddressFragment.newInstance()
            else -> Fragment()
        }

    fun getFragmentWithPortfolio(pageName: String, iPortfolio: IPortfolio?): Fragment =
        when (pageName) {
            ADD_PORTFOLIO -> PortfolioFragment.newInstance()
            EDIT_PORTFOLIO -> PortfolioFragment.newInstance(iPortfolio as Portfolio)
            ADD_REPAIR_PHOTO -> RepairPhotoFragment.newInstance()
            EDIT_REPAIR_PHOTO -> RepairPhotoFragment.newInstance(iPortfolio as RepairPhoto)
            ADD_PRICE_BY_PROJECTS -> PriceByProjectFragment.newInstance()
            EDIT_PRICE_BY_PROJECTS -> PriceByProjectFragment.newInstance(iPortfolio as PriceByProject)
            else -> Fragment()
        }
}