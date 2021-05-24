package kr.co.soogong.master.uiinterface.auth.password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.auth.password.change.ChangePasswordActivity

object ChangePasswordActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY_CHANGE_PASSWORD"
    private const val EXTRA_STRING_KEY = "EXTRA_STRING_KEY_CHANGE_PASSWORD"
    private const val EXTRA_STRING_KEY_FOR_ID = "EXTRA_STRING_KEY_FOR_ID"

    const val FROM_SIGN_IN = "FROM_SIGN_IN"
    const val FROM_MY_PAGE = "FROM_MY_PAGE"


    fun getIntent(context: Context, activityFrom: String, id: String?): Intent {
        return Intent(context, ChangePasswordActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, activityFrom)
                putString(EXTRA_STRING_KEY_FOR_ID, id)
            })
        }
    }

    fun getActivityFrom(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(EXTRA_STRING_KEY, "") ?: ""
    }

    fun getId(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(EXTRA_STRING_KEY_FOR_ID, "") ?: ""
    }
}