package kr.co.soogong.master.uihelper.requirment.action.end

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.requirement.action.end.EndRepairActivity

object EndRepairActivityHelper {
    private const val BUNDLE_KEY = "END_ESTIMATION_BUNDLE_KEY"
    private const val EXTRA_INT_KEY = "END_ESTIMATION_EXTRA_INT_KEY"

    fun getIntent(context: Context?, requirementId: Int): Intent {
        return Intent(context, EndRepairActivity::class.java).apply {
            putExtra(BUNDLE_KEY, Bundle().apply {
                putInt(EXTRA_INT_KEY, requirementId)
            })
        }
    }

    fun getRequirementId(intent: Intent): Int {
        return intent.getBundleExtra(BUNDLE_KEY)?.getInt(EXTRA_INT_KEY, 0) ?: 0
    }
}