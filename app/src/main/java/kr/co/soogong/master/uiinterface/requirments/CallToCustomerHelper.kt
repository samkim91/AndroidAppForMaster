package kr.co.soogong.master.uiinterface.requirments

import android.content.Context
import android.content.Intent
import android.net.Uri
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper

object CallToCustomerHelper {
    fun getIntent(phoneNumber: String): Intent {
        return Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    }
}