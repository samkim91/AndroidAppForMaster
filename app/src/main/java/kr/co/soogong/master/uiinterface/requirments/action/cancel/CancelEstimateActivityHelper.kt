package kr.co.soogong.master.uiinterface.requirments.action.cancel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirements.action.cancel.CancelEstimateActivity

object CancelEstimateActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "CANCEL_ESTIMATE_BUNDLE_KEY"
    private const val EXTRA_STRING_KEY = "CANCEL_ESTIMATE_EXTRA_STRING_KEY"


    fun getIntent(context: Context, keycode: String): Intent {
        return Intent(context, CancelEstimateActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, keycode)
            })
        }
    }

    fun getEstimationId(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(
            EXTRA_STRING_KEY, ""
        ) ?: ""
    }


}