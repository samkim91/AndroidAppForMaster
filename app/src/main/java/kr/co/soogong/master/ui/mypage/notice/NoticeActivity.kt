package kr.co.soogong.master.ui.mypage.notice

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityNoticeBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.mypage.NoticeAdapter
import kr.co.soogong.master.uiinterface.mypage.notice.detail.NoticeDetailActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class NoticeActivity : BaseActivity<ActivityNoticeBinding>(
    R.layout.activity_notice
) {
    private val viewModel: NoticeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@NoticeActivity

            list.adapter = NoticeAdapter(NoticeViewHolder.NoticeView) {
                startActivity(NoticeDetailActivityHelper.getIntent(this@NoticeActivity, it))
            }

            with(actionBar) {
                title.text = getString(R.string.notice_page_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.getNoticeList()
    }

    companion object {
        private const val TAG = "NoticeActivity"
    }
}