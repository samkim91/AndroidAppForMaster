package kr.co.soogong.master.uiinterface.sign.find

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.sign.find.FindInfoActivity

object FindInfoActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, FindInfoActivity::class.java)
    }
}