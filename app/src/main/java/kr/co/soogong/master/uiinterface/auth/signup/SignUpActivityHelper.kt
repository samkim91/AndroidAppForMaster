package kr.co.soogong.master.uiinterface.auth.signup

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.auth.signup.SignUpActivity

object SignUpActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, SignUpActivity::class.java)
    }
}