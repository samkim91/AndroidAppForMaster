package kr.co.soogong.master.presentation.uihelper.preferences

import androidx.fragment.app.Fragment
import kr.co.soogong.master.presentation.ui.preferences.detail.alarm.AlarmFragment
import kr.co.soogong.master.presentation.ui.preferences.detail.customerservice.CustomerServiceFragment
import kr.co.soogong.master.presentation.ui.preferences.detail.notice.NoticeFragment

object PreferencesDetailFragmentHelper {

    // 선택 정보
    const val NOTICE_PAGE = "공지사항"
    const val SETTING_ALARM_PAGE = "알림설정"
    const val CUSTOMER_SERVICE_PAGE = "고객센터"

    fun getFragment(pageName: String): Fragment =
        when (pageName) {
            NOTICE_PAGE -> NoticeFragment.newInstance()
            SETTING_ALARM_PAGE -> AlarmFragment.newInstance()
            CUSTOMER_SERVICE_PAGE -> CustomerServiceFragment.newInstance()
            else -> Fragment()

        }
}