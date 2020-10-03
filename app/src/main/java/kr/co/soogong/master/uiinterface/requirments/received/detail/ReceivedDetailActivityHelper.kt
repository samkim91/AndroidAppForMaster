package kr.co.soogong.master.uiinterface.requirments.received.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirements.received.detail.ReceivedDetailActivity

object ReceivedDetailActivityHelper {
    const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_RECEIVED"
    const val BUNDLE_KEY_RECEIVED_KEY = "BUNDLE_KEY_RECEIVED_KEY_RECEIVED"

    fun getIntent(context: Context, keycode: String): Intent {
        return Intent(context, ReceivedDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putString(BUNDLE_KEY_RECEIVED_KEY, keycode)
            })
        }
    }
}