package kr.co.soogong.master.uihelper.requirment.action

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementActivity

object ViewRequirementActivityHelper {
    const val BUNDLE_KEY = "BUNDLE_KEY"
    const val REQUIREMENT_ID = "REQUIREMENT_ID"

    fun getIntent(context: Context, id: Int): Intent {
        return Intent(context, ViewRequirementActivity::class.java).apply {
            putExtra(BUNDLE_KEY, Bundle().apply {
                putInt(REQUIREMENT_ID, id)
            })
        }
    }

    fun getRequirementId(intent: Intent): Int {
        return intent.getBundleExtra(BUNDLE_KEY)?.getInt(REQUIREMENT_ID, 0) ?: 0
    }

    fun getRequirementIdBySaveState(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle.get<Bundle>(BUNDLE_KEY)?.getInt(REQUIREMENT_ID) ?: 0
    }
}