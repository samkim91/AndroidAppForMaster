package kr.co.soogong.master.uiinterface.sign

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.sign.SignMainActivity

object SignMainActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, SignMainActivity::class.java)
    }
}