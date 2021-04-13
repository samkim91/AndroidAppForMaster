package kr.co.soogong.master.uiinterface.requirments.action.end

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirements.action.end.EndEstimateActivity

object EndEstimateActivityHelper {
    const val BUNDLE_KEY = "END_ESTIMATION_BUNDLE_KEY"
    const val EXTRA_STRING_KEY = "END_ESTIMATION_EXTRA_STRING_KEY"

    fun getIntent(context: Context?, keycode: String): Intent {
        return Intent(context, EndEstimateActivity::class.java).apply {
            putExtra(BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, keycode)
            })
        }
    }

    fun getEstimationId(intent: Intent): String {
        return intent.getBundleExtra(BUNDLE_KEY)?.getString(EXTRA_STRING_KEY, "") ?: ""
    }
}