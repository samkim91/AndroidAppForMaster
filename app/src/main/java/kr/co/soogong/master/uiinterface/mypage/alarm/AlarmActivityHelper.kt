package kr.co.soogong.master.uiinterface.mypage.alarm

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.mypage.alarm.AlarmActivity

object AlarmActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, AlarmActivity::class.java)
    }
}