package kr.co.soogong.master.uihelper.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerActivity

object EditProfileContainerActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY"
    private const val EXTRA_STRING_KEY = "EXTRA_STRING_KEY"
    private const val EXTRA_INT_KEY = "EXTRA_INT_KEY"

    // [Note] Edit Profile Container Activity 는 1depth 로 이동하는 경우와 2depth 로 이동하는 경우가 있다.
    // 2depth 는 포트폴리오, 시공 종류별 가격 수정하는 경우이다. 앞의 두 경우는 "해당 포트폴리오, 시공 종류별 가격" item 의 id를 가지고 activity 로 이동해야하므로,
    // itemId는 nullable parameter 이다.
    fun getIntent(context: Context, pageName: String, itemId: Int? = null): Intent {
        return Intent(context, EditProfileContainerActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, pageName)
                itemId?.let { putInt(EXTRA_INT_KEY, it) }
            })
        }
    }

    fun getPageName(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(EXTRA_STRING_KEY, "") ?: ""
    }

    fun getItemId(intent: Intent): Int? {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getInt(EXTRA_INT_KEY)
    }
}