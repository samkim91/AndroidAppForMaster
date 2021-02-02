package kr.co.soogong.master.uiinterface.mypage.account

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.mypage.account.AccountActivity

object AccountActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, AccountActivity::class.java)
    }
}