package kr.co.soogong.master.uihelper.profile

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationActivity

object EditRequiredInformationActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, EditRequiredInformationActivity::class.java)
    }
}
