package kr.co.soogong.master.uiinterface.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.profile.edit.EditProfileContainerActivity

object EditProfileContainerActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY_EDIT_PROFILE_DETAIL"
    private const val EXTRA_STRING_KEY = "EXTRA_STRING_KEY_EDIT_PROFILE_DETAIL"
    private const val EXTRA_INT_KEY = "EXTRA_INT_KEY_EDIT_PROFILE_DETAIL"

    fun getIntent(context: Context, pageName: String): Intent {
        return Intent(context, EditProfileContainerActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, pageName)
            })
        }
    }

    fun getIntent(context: Context, pageName: String, itemId: Int): Intent {
        return Intent(context, EditProfileContainerActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, pageName)
                putInt(EXTRA_INT_KEY, itemId)
            })
        }
    }

    fun getPageName(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(EXTRA_STRING_KEY, "") ?: ""
    }

    fun getItemId(intent: Intent): Int {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getInt(EXTRA_INT_KEY, -1) ?: -1
    }
}