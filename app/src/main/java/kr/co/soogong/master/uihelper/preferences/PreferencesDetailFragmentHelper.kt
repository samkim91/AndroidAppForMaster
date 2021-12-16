package kr.co.soogong.master.uihelper.preferences

import androidx.fragment.app.Fragment
import kr.co.soogong.master.ui.preferences.detail.notice.NoticeFragment

object PreferencesDetailFragmentHelper {

    // 선택 정보
    const val NOTICE_PAGE = "공지사항"
    const val SETTING_ALARM_PAGE = "알림설정"
    const val CUSTOMER_SERVICE_PAGE = "고객센터"

    fun getFragment(pageName: String): Fragment =
        when (pageName) {
            NOTICE_PAGE -> NoticeFragment.newInstance()

            else -> Fragment()

        }
}