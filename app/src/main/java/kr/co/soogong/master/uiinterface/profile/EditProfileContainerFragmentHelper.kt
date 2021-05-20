package kr.co.soogong.master.uiinterface.profile

import androidx.fragment.app.Fragment
import kr.co.soogong.master.ui.profile.edit.flexiblecost.EditFlexibleCostFragment
import kr.co.soogong.master.ui.profile.edit.otherflexibleoptions.EditOtherFlexibleOptionsFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.address.EditAddressFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.briefintroduction.EditBriefIntroductionFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businessrepresentativename.EditBusinessRepresentativeNameFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businesstypes.EditBusinessTypesFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businessunitinformation.EditBusinessUnitInformationFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.phonenumber.EditPhoneNumberFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.representativeimages.EditRepresentativeImagesFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.warrantyinformation.EditWarrantyInformationFragment
import kr.co.soogong.master.ui.profile.edit.withcard.portfolio.EditPortfolioFragment
import kr.co.soogong.master.ui.profile.edit.withcard.pricebyproject.EditPriceByProjectFragment

object EditProfileContainerFragmentHelper {
    const val TAG = "EditProfileContainerFragmentHelper"

    const val EDIT_PORTFOLIO = "포트폴리오 수정하기"
    const val ADD_PORTFOLIO = "포트폴리오 추가하기"
    const val EDIT_PRICE_BY_PROJECTS = "시공 종류별 가격 수정하기"
    const val ADD_PRICE_BY_PROJECTS = "시공 종류별 가격 추가하기"
    const val EDIT_FLEXIBLE_COST = "현장 가격 변동 요인 등록·수정하기"
    const val EDIT_OTHER_FLEXIBLE_OPTIONS = "기타 변동 가능사항 등록·수정하기"

    const val EDIT_BRIEF_INTRODUCTION = "업체 소개 등록·수정하기"
    const val EDIT_COMPANY_IMAGE = "대표 이미지 편집하기"
    const val EDIT_BUSINESS_UNIT_INFORMATION = "사업자 정보 등록·수정하기"
    const val EDIT_WARRANTY_INFORMATION = "A/S 정보 등록·수정하기"
    const val EDIT_BUSINESS_REPRESENTATIVE_NAME = "대표자명 등록·수정하기"
    const val EDIT_PHONE_NUMBER = "휴대폰번호 수정하기"
    const val EDIT_ADDRESS = "업체 주소 수정하기"
    const val EDIT_BUSINESS_TYPES = "시공 업종 수정하기"


//    const val EDIT_NUMBER_OF_FELLOW = "팀원 수 수정하기"
//    const val EDIT_AVAILABLE_TIME_FOR_CONTACT = "연락 가능 시간 수정하기"


    fun getFragment(pageName: String, itemId: Int = -1): Fragment =
        when (pageName) {
            ADD_PORTFOLIO -> EditPortfolioFragment.newInstance(ADD_PORTFOLIO, null)
            EDIT_PORTFOLIO -> EditPortfolioFragment.newInstance(EDIT_PORTFOLIO, itemId)
            ADD_PRICE_BY_PROJECTS -> EditPriceByProjectFragment.newInstance(ADD_PRICE_BY_PROJECTS, null)
            EDIT_PRICE_BY_PROJECTS -> EditPriceByProjectFragment.newInstance(EDIT_PRICE_BY_PROJECTS, itemId)
            EDIT_FLEXIBLE_COST -> EditFlexibleCostFragment.newInstance()
            EDIT_OTHER_FLEXIBLE_OPTIONS -> EditOtherFlexibleOptionsFragment.newInstance()
            EDIT_BRIEF_INTRODUCTION -> EditBriefIntroductionFragment.newInstance()
            EDIT_COMPANY_IMAGE -> EditRepresentativeImagesFragment.newInstance()
            EDIT_BUSINESS_UNIT_INFORMATION -> EditBusinessUnitInformationFragment.newInstance()
            EDIT_WARRANTY_INFORMATION -> EditWarrantyInformationFragment.newInstance()
            EDIT_BUSINESS_REPRESENTATIVE_NAME -> EditBusinessRepresentativeNameFragment.newInstance()
            EDIT_PHONE_NUMBER -> EditPhoneNumberFragment.newInstance()
            EDIT_BUSINESS_TYPES -> EditBusinessTypesFragment.newInstance()
            EDIT_ADDRESS -> EditAddressFragment.newInstance()
            else -> Fragment()
        }
}