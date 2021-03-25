package kr.co.soogong.master.uiinterface.requirments.action.end

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.requirements.action.end.EndEstimateActivity

object EndEstimateActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, EndEstimateActivity::class.java)
    }
}