package kr.co.soogong.master.ui.settings.notice.detail

import android.os.Bundle
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.databinding.ActivityNoticeDetailBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.settings.notice.detail.NoticeDetailActivityHelper
import timber.log.Timber

class NoticeDetailActivity : BaseActivity<ActivityNoticeDetailBinding>(
    R.layout.activity_notice_detail
) {
    private val noticeData: Notice by lazy {
        intent.getBundleExtra(NoticeDetailActivityHelper.EXTRA_KEY_BUNDLE)
            ?.getParcelable(NoticeDetailActivityHelper.BUNDLE_KEY_PARCELABLE_KEY) as? Notice
            ?: Notice.NULL_OBJECT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            setVariable(BR.data, noticeData)
            lifecycleOwner = this@NoticeDetailActivity
            with(actionBar) {
                title.text = "공지 사항"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }
    }

    companion object {
        private const val TAG = "NoticeDetailActivity"
    }
}

