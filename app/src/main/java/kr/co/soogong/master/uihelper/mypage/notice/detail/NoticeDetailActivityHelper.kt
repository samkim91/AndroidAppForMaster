package kr.co.soogong.master.uihelper.mypage.notice.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import kr.co.soogong.master.ui.preferences.notice.detail.NoticeDetailActivity

object NoticeDetailActivityHelper {
    private const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_NOTICE_DETAIL"
    private const val BUNDLE_KEY_PARCELABLE_KEY = "BUNDLE_KEY_PARCELABLE_KEY_NOTICE_DETAIL"

    fun getIntent(context: Context, noticeId: Int): Intent {
        return Intent(context, NoticeDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putInt(BUNDLE_KEY_PARCELABLE_KEY, noticeId)
            })
        }
    }

    fun getNoticeId(intent: Intent): Int {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getInt(BUNDLE_KEY_PARCELABLE_KEY)!!
    }

    fun getNoticeIdFromSavedState(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle.get<Bundle>(EXTRA_KEY_BUNDLE)?.getInt(
            BUNDLE_KEY_PARCELABLE_KEY
        ) ?: 0
    }
}