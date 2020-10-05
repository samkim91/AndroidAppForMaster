package kr.co.soogong.master.uiinterface.sign.signin

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.sign.signin.SignInActivity

object SignInActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, SignInActivity::class.java)
    }
}