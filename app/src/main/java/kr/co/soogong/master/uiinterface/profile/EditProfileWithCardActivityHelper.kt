package kr.co.soogong.master.uiinterface.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.profile.edit.EditProfileWithCardActivity
import kr.co.soogong.master.ui.requirements.action.view.ViewEstimateActivity

object EditProfileWithCardActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY_EDIT_PROFILE_WITH_CARD"
    private const val EXTRA_STRING_KEY = "EXTRA_STRING_KEY_EDIT_PROFILE_WITH_CARD"

    fun getIntent(context: Context, pageName: String): Intent {
        return Intent(context, EditProfileWithCardActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, pageName)
            })
        }
    }

    fun getPageName(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(EXTRA_STRING_KEY, "") ?: ""
    }
}