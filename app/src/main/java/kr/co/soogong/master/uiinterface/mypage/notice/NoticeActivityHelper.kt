package kr.co.soogong.master.uiinterface.mypage.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.ui.mypage.notice.NoticeActivity

object NoticeActivityHelper {
    const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_NOTICE"
    const val BUNDLE_KEY_PARCELABLE_KEY = "BUNDLE_KEY_PARCELABLE_KEY_NOTICE"

    fun getIntent(context: Context, notice: Notice? = null): Intent {
        return Intent(context, NoticeActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putParcelable(BUNDLE_KEY_PARCELABLE_KEY, notice)
            })
        }
    }
}