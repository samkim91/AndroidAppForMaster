package kr.co.soogong.master.uihelper.requirment.action.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirement.action.view.ViewEstimateActivity

object ViewEstimateActivityHelper {
    const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_VIEW_ESTIMATE"
    const val BUNDLE_KEY_ESTIMATION_KEY = "BUNDLE_KEY_ESTIMATION_KEY"

    fun getIntent(context: Context, id: Int): Intent {
        return Intent(context, ViewEstimateActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putInt(BUNDLE_KEY_ESTIMATION_KEY, id)
            })
        }
    }

    fun getEstimationId(intent: Intent): Int {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getInt(BUNDLE_KEY_ESTIMATION_KEY, 0) ?: 0
    }
}