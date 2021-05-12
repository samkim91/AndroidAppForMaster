package kr.co.soogong.master.uiinterface.profile

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.profile.edit.requiredinformation.EditRequiredInformationActivity

object EditRequiredInformationActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, EditRequiredInformationActivity::class.java)
    }
}
