package kr.co.soogong.master.uihelper.requirment

import android.content.Intent
import android.net.Uri

object CallToCustomerHelper {
    fun getIntent(phoneNumber: String?): Intent {
        return Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    }
}