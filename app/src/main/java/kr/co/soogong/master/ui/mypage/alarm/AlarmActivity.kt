package kr.co.soogong.master.ui.mypage.alarm

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAlarmBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

@AndroidEntryPoint
class AlarmActivity : BaseActivity<ActivityAlarmBinding>(
    R.layout.activity_alarm
) {

    private val viewModel: AlarmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        viewModel.getAlarmStatus()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@AlarmActivity

            with(actionBar) {
                title.text = getString(R.string.alarm_page_name)
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