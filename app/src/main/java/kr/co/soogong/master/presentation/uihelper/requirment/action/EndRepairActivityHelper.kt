package kr.co.soogong.master.presentation.uihelper.requirment.action

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import kr.co.soogong.master.presentation.ui.requirement.action.end.EndRepairActivity

object EndRepairActivityHelper {
    private const val BUNDLE_KEY = "BUNDLE_KEY"
    private const val REQUIREMENT_ID = "REQUIREMENT_ID"

    fun getIntent(context: Context?, requirementId: Int): Intent {
        return Intent(context, EndRepairActivity::class.java).apply {
            putExtra(BUNDLE_KEY, Bundle().apply {
                putInt(REQUIREMENT_ID, requirementId)
            })
        }
    }

    fun getRequirementId(intent: Intent): Int {
        return intent.getBundleExtra(BUNDLE_KEY)?.getInt(REQUIREMENT_ID, 0) ?: 0
    }

    fun getRequirementIdFromSavedState(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle.get<Bundle>(BUNDLE_KEY)?.getInt(REQUIREMENT_ID) ?: 0
    }
}