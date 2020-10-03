package kr.co.soogong.master.uiinterface.requirments.received.estimate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirements.received.estimate.EstimateActivity

object EstimateActivityHelper {
    const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_ESTIMATE"
    const val BUNDLE_KEY_RECEIVED_KEY = "BUNDLE_KEY_RECEIVED_KEY_ESTIMATE"

    fun getIntent(context: Context, keycode: String): Intent {
        return Intent(context, EstimateActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putString(BUNDLE_KEY_RECEIVED_KEY, keycode)
            })
        }
    }
}