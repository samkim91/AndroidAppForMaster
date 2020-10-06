package kr.co.soogong.master.uiinterface.sign.signin.find

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.sign.signin.find.FindInfoActivity

object FindInfoActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, FindInfoActivity::class.java)
    }
}