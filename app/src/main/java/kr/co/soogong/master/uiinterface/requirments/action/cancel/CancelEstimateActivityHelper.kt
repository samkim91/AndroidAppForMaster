package kr.co.soogong.master.uiinterface.requirments.action.cancel

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.requirements.action.cancel.CancelEstimateActivity

object CancelEstimateActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, CancelEstimateActivity::class.java)
    }
}