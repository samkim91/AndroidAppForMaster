package kr.co.soogong.master.uihelper.requirment

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.requirement.list.done.DoneActivity

object DoneActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, DoneActivity::class.java)
    }
}