package kr.co.soogong.master.uihelper.common

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.common.KakaoAddressActivity

object KakaoAddressActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, KakaoAddressActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }

    fun getAddressFromIntent(intent: Intent) = intent.extras?.getString(ADDRESS)

    const val AREA = "AREA"
    const val LOCATION = "LOCATION"
    const val ADDRESS = "ADDRESS"
}