package kr.co.soogong.master.uiinterface.settings.alarm

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.settings.alarm.AlarmActivity

object AlarmActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, AlarmActivity::class.java)
    }
}