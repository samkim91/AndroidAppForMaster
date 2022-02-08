package kr.co.soogong.master.presentation.uihelper.requirment

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.presentation.ui.requirement.list.search.SearchActivity

object SearchActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, SearchActivity::class.java)
    }
}