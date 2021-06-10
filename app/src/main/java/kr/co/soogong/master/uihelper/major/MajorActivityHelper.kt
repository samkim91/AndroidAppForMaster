package kr.co.soogong.master.uihelper.major

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.major.MajorActivity

object MajorActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, MajorActivity::class.java)
    }

    const val SEARCH_CATEGORY_ACTIVITY = 10001

    const val BUNDLE_BUSINESS_TYPE = "BUNDLE_CATEGORY"
    const val BUNDLE_CATEGORY = "BUNDLE_CATEGORY"
    const val BUNDLE_PROJECT_LIST = "BUNDLE_PROJECT_LIST"
}