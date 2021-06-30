package kr.co.soogong.master.ui.mypage.notice.detail

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityNoticeDetailBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.mypage.notice.detail.NoticeDetailActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class NoticeDetailActivity : BaseActivity<ActivityNoticeDetailBinding>(
    R.layout.activity_notice_detail
) {
    private val viewModel: NoticeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@NoticeDetailActivity

            with(actionBar) {
                title.text = "공지 사항"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestNotice()
    }

    companion object {
        private const val TAG = "NoticeDetailActivity"
    }
}

