package kr.co.soogong.master.ui.mypage.notice.detail

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.databinding.ActivityNoticeDetailBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.mypage.notice.detail.NoticeDetailActivityHelper
import timber.log.Timber

class NoticeDetailActivity : BaseActivity<ActivityNoticeDetailBinding>(
    R.layout.activity_notice_detail
) {
    private val noticeData: Notice by lazy {
        NoticeDetailActivityHelper.getNoticeData(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            data = noticeData
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

