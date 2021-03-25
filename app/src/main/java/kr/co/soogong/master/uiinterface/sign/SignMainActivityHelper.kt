package kr.co.soogong.master.uiinterface.sign

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.sign.SignMainActivity

object SignMainActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, SignMainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}