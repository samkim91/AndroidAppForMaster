package kr.co.soogong.master.uiinterface.mypage.notice.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.ui.mypage.notice.detail.NoticeDetailActivity

object NoticeDetailActivityHelper {
    const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_NOTICE_DETAIL"
    private const val BUNDLE_KEY_PARCELABLE_KEY = "BUNDLE_KEY_PARCELABLE_KEY_NOTICE_DETAIL"

    fun getIntent(context: Context, notice: Notice): Intent {
        return Intent(context, NoticeDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putParcelable(BUNDLE_KEY_PARCELABLE_KEY, notice)
            })
        }
    }

    fun getNoticeData(intent: Intent): Notice {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)
            ?.getParcelable(BUNDLE_KEY_PARCELABLE_KEY) as? Notice
            ?: Notice.NULL_OBJECT
    }
}