package kr.co.soogong.master.uihelper.preferences

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.preferences.detail.PreferencesContainerActivity

object PreferencesContainerActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY"
    private const val EXTRA_STRING_KEY = "EXTRA_STRING_KEY"

    fun getIntent(context: Context, pageName: String): Intent {
        return Intent(context, PreferencesContainerActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, pageName)
            })
        }
    }

    fun getPageName(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(EXTRA_STRING_KEY, "") ?: ""
    }
}