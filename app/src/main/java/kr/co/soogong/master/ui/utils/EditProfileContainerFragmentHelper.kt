package kr.co.soogong.master.ui.utils

import androidx.fragment.app.Fragment
import kr.co.soogong.master.ui.profile.edit.portfolio.EditPortfolioFragment
import kr.co.soogong.master.ui.profile.edit.pricebyproject.EditPriceByProjectFragment
import timber.log.Timber

object EditProfileContainerFragmentHelper {
    const val TAG = "EditProfileContainerFragmentHelper"

    const val EDIT_PORTFOLIO = "포트폴리오 수정하기"
    const val ADD_PORTFOLIO = "포트폴리오 추가하기"
    const val EDIT_PRICE_BY_PROJECTS = "시공 종류별 가격 수정하기"
    const val ADD_PRICE_BY_PROJECTS = "시공 종류별 가격 추가하기"
    const val EDIT_WARRANTY_INFORMATION = "A/S 정보 수정하기"
    const val EDIT_FLEXIBLE_COST = "현장 가격 변동 요인 등록·수정하기"
    const val EDIT_COMPANY_NAME = "업체 이름 수정하기"
    const val EDIT_BRIEF_INTRODUCTION = "업체 소개 수정하기"
    const val EDIT_BUSINESS_REPRESENTATIVE = "사업자 대표명 수정하기"
    const val EDIT_PHONE_NUMBER = "연락처 수정하기"
    const val EDIT_ADDRESS = "업체 주소 수정하기"
    const val EDIT_NUMBER_OF_FELLOW = "팀원 수 수정하기"
    const val EDIT_AVAILABLE_TIME_FOR_CONTACT = "연락 가능 시간 수정하기"
    const val EDIT_MAJOR = "시공 업종 수정하기"
    const val EDIT_OTHER_FLEXIBLE_OPTIONS = "기타 변동 가능사항 등록·수정하기"

    fun getFragment(pageName: String, itemId: Int = -1) : Fragment =
        when(pageName) {
            ADD_PORTFOLIO -> EditPortfolioFragment.newInstance(ADD_PORTFOLIO, null)
            EDIT_PORTFOLIO -> EditPortfolioFragment.newInstance(EDIT_PORTFOLIO, itemId)
            ADD_PRICE_BY_PROJECTS -> EditPriceByProjectFragment.newInstance(ADD_PRICE_BY_PROJECTS, null)
            EDIT_PRICE_BY_PROJECTS -> EditPriceByProjectFragment.newInstance(EDIT_PRICE_BY_PROJECTS, itemId)
            else -> Fragment()
        }
}