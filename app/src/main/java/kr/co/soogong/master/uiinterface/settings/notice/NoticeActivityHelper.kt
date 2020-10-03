package kr.co.soogong.master.uiinterface.settings.notice

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.settings.notice.NoticeActivity

object NoticeActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, NoticeActivity::class.java)
    }
}