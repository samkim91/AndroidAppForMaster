package kr.co.soogong.master.presentation.uihelper.requirment.action

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import kr.co.soogong.master.presentation.ui.requirement.action.write.MeasurementActivity

object MeasureActivityHelper {
    private const val BUNDLE_KEY = "BUNDLE_KEY"
    private const val REQUIREMENT_ID = "REQUIREMENT_ID"

    fun getIntent(context: Context, id: Int): Intent {
        return Intent(context, MeasurementActivity::class.java).apply {
            putExtra(BUNDLE_KEY, Bundle().apply {
                putInt(REQUIREMENT_ID, id)
            })
        }
    }

    fun getRequirementIdFromSavedState(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle.get<Bundle>(BUNDLE_KEY)?.getInt(REQUIREMENT_ID) ?: 0
    }
}