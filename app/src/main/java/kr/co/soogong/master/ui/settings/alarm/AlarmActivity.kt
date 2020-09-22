package kr.co.soogong.master.ui.settings.alarm

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAlarmBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

class AlarmActivity : BaseActivity<ActivityAlarmBinding>(
    R.layout.activity_alarm
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            with(actionBar) {
                title.text = "알림 설정"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }
    }

    companion object {
        private const val TAG = "AlarmActivity"
    }
}