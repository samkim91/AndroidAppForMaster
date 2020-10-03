package kr.co.soogong.master.uiinterface.settings.password

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.settings.password.PasswordActivity

object PasswordActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, PasswordActivity::class.java)
    }
}