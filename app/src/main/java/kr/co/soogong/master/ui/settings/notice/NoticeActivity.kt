package kr.co.soogong.master.ui.settings.notice

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityNoticeBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

class NoticeActivity : BaseActivity<ActivityNoticeBinding>(
    R.layout.activity_notice
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
    }

    companion object {
        private const val TAG = "NoticeActivity"
    }
}