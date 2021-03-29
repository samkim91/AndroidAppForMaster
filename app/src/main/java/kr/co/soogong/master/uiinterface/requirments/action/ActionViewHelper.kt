package kr.co.soogong.master.uiinterface.requirments.action

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper

object ActionViewHelper {
    fun getIntent(context: Context, keycode: String, estimationStatus: EstimationStatus): Intent {
        return when (estimationStatus) {
            /*
            EstimationStatus.Cancel -> {
            }
            EstimationStatus.CustomDone -> {
            }
            EstimationStatus.Done -> {
            }
            EstimationStatus.Final -> {
            }
            EstimationStatus.Progress -> {
            }
            EstimationStatus.Request -> {
            }
            EstimationStatus.Waiting -> {
            }
             */
            else -> ViewEstimateActivityHelper.getIntent(context, keycode)
        }
    }
}