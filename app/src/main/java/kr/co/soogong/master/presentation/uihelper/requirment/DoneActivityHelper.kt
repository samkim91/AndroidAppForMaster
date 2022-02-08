package kr.co.soogong.master.presentation.uihelper.requirment

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.presentation.ui.requirement.list.done.DoneActivity

object DoneActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, DoneActivity::class.java)
    }
}