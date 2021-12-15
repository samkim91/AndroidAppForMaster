package kr.co.soogong.master.uihelper.mypage.notice

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.preferences.notice.NoticeActivity

object NoticeActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, NoticeActivity::class.java)
    }
}