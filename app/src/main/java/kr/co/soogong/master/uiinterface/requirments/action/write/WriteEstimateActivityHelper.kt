package kr.co.soogong.master.uiinterface.requirments.action.write

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.requirements.action.write.WriteEstimateActivity

object WriteEstimateActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, WriteEstimateActivity::class.java)
    }
}