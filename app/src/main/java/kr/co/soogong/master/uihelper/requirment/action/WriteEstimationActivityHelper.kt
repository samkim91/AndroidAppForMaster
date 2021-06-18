package kr.co.soogong.master.uihelper.requirment.action

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationActivity

object WriteEstimationActivityHelper {
    const val BUNDLE_KEY = "WRITE_ESTIMATION_BUNDLE_KEY"
    const val EXTRA_STRING_KEY = "WRITE_ESTIMATION_EXTRA_STRING_KEY"

    fun getIntent(context: Context, id: Int): Intent {
        return Intent(context, WriteEstimationActivity::class.java).apply {
            putExtra(BUNDLE_KEY, Bundle().apply {
                putInt(EXTRA_STRING_KEY, id)
            })
        }
    }

    fun getEstimationId(intent: Intent): Int {
        return intent.getBundleExtra(BUNDLE_KEY)?.getInt(EXTRA_STRING_KEY, 0) ?: 0
    }
}