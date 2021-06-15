package kr.co.soogong.master.uihelper.main

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.main.MainActivity

object MainActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}