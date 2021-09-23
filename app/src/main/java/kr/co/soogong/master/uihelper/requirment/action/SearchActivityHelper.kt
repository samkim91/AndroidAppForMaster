package kr.co.soogong.master.uihelper.requirment.action

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.requirement.action.search.SearchActivity

object SearchActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, SearchActivity::class.java)
    }
}