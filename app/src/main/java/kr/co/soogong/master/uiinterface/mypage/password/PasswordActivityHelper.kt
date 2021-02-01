package kr.co.soogong.master.uiinterface.mypage.password

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.mypage.password.PasswordActivity

object PasswordActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, PasswordActivity::class.java)
    }
}