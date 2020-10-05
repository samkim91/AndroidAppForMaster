package kr.co.soogong.master.uiinterface.requirments.progress.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirements.progress.detail.ProgressDetailActivity

object ProgressDetailActivityHelper {
    const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_PROGRESS"
    const val BUNDLE_KEY_RECEIVED_KEY = "BUNDLE_KEY_RECEIVED_KEY_PROGRESS"

    fun getIntent(context: Context, keycode: String): Intent {
        return Intent(context, ProgressDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putString(BUNDLE_KEY_RECEIVED_KEY, keycode)
            })
        }
    }
}