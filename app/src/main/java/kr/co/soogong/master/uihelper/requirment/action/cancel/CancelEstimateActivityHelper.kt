package kr.co.soogong.master.uihelper.requirment.action.cancel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirement.action.cancel.CancelEstimateActivity

object CancelEstimateActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "CANCEL_ESTIMATE_BUNDLE_KEY"
    private const val EXTRA_INT_KEY = "CANCEL_ESTIMATE_EXTRA_INT_KEY"


    fun getIntent(context: Context, requirementId: Int): Intent {
        return Intent(context, CancelEstimateActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putInt(EXTRA_INT_KEY, requirementId)
            })
        }
    }

    fun getEstimationId(intent: Intent): Int {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getInt(
            EXTRA_INT_KEY, 0
        ) ?: 0
    }


}