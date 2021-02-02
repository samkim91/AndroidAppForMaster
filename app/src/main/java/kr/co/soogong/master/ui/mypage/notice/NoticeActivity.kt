package kr.co.soogong.master.ui.mypage.notice

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityNoticeBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.mypage.NoticeAdapter
import kr.co.soogong.master.uiinterface.mypage.notice.detail.NoticeDetailActivityHelper
import timber.log.Timber

class NoticeActivity : BaseActivity<ActivityNoticeBinding>(
    R.layout.activity_notice
) {
    private val viewModel: NoticeViewModel by lazy {
        ViewModelProvider(this).get(NoticeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@NoticeActivity

            list.adapter = NoticeAdapter(NoticeListViewHolder.NoticeListView) {
                startActivity(NoticeDetailActivityHelper.getIntent(this@NoticeActivity, it))
            }

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
        Timber.tag(TAG).d("onStart: ")
        viewModel.getNoticeList()
    }

    companion object {
        private const val TAG = "NoticeActivity"
    }
}