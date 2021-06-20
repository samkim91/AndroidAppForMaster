package kr.co.soogong.master.uihelper.requirment.action

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementActivity

object ViewRequirementActivityHelper {
    const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_VIEW_ESTIMATE"
    const val BUNDLE_KEY_REQUIREMENT_KEY = "BUNDLE_KEY_REQUIREMENT_KEY"

    fun getIntent(context: Context, id: Int): Intent {
        return Intent(context, ViewRequirementActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putInt(BUNDLE_KEY_REQUIREMENT_KEY, id)
            })
        }
    }

    fun getRequirementId(intent: Intent): Int {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getInt(BUNDLE_KEY_REQUIREMENT_KEY, 0) ?: 0
    }
}