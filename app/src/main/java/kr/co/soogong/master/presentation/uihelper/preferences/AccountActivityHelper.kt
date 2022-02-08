package kr.co.soogong.master.presentation.uihelper.preferences

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.presentation.ui.preferences.account.AccountActivity

object AccountActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, AccountActivity::class.java)
    }
}