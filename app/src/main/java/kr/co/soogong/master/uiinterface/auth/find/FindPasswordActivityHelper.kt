package kr.co.soogong.master.uiinterface.auth.find

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.auth.find.FindPasswordActivity

object FindPasswordActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, FindPasswordActivity::class.java)
    }
}