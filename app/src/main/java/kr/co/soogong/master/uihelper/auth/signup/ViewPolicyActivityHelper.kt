package kr.co.soogong.master.uihelper.auth.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.auth.signup.steps.ViewPolicyActivity

object ViewPolicyActivityHelper {
    private const val BUNDLE_KEY = "BUNDLE_KEY"
    private const val STRING_KEY = "STRING_KEY"

    const val TERMS_OF_SERVICE = "TERMS"
    const val PRIVATE_POLICY = "POLICY"

    fun getIntent(context: Context, type: String): Intent {
        return Intent(context, ViewPolicyActivity::class.java).apply {
            putExtra(BUNDLE_KEY, Bundle().apply {
                putString(STRING_KEY, type)
            })
        }
    }

    fun getDocumentType(intent: Intent): String {
        return intent.getBundleExtra(BUNDLE_KEY)?.getString(STRING_KEY, "") ?: ""
    }
}