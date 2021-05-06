package kr.co.soogong.master.uiinterface.auth.signup

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.auth.signup.AddressActivity

object AddressActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, AddressActivity::class.java)
    }

    const val SEARCH_ADDRESS_ACTIVITY = 10000

    const val AREA = "AREA"
    const val LOCATION = "LOCATION"
    const val ADDRESS = "ADDRESS"
}