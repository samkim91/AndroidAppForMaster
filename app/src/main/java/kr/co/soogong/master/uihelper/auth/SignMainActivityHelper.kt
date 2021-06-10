package kr.co.soogong.master.uihelper.auth

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.auth.SignMainActivity

object SignMainActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, SignMainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}