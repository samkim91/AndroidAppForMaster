package kr.co.soogong.master.uihelper.auth.signin

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.auth.signin.SignInActivity

object SignInActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, SignInActivity::class.java)
    }
}