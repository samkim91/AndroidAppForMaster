package kr.co.soogong.master.uiinterface.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.profile.edit.EditProfileContainerActivity

object EditProfileContainerActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY_EDIT_PROFILE_DETAIL"
    private const val EXTRA_STRING_KEY = "EXTRA_STRING_KEY_EDIT_PROFILE_DETAIL"
    private const val EXTRA_INT_KEY = "EXTRA_INT_KEY_EDIT_PROFILE_DETAIL"

    // Edit Profile Container Activity는 1 depth로 이동하는 경우와 2 depth로 이동하는 경우가 있다.
    // 2 depth는 포트폴리오, 시공 종류별 가격 수정하는 경우이다. 앞의 두 경우는 해당 item의 id를 가지고 이 activity로 이동해야하므로,
    // getIntent를 2가지 경우로 나누어 구성했다.
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