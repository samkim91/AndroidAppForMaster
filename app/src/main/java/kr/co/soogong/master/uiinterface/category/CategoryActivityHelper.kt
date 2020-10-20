package kr.co.soogong.master.uiinterface.category

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.select.CategoryActivity

object CategoryActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, CategoryActivity::class.java)
    }

    const val SEARCH_CATEGORY_ACTIVITY = 10001

    const val BUNDLE_CATEGORY = "BUNDLE_CATEGORY"
    const val BUNDLE_PROJECT_LIST = "BUNDLE_PROJECT_LIST"
}