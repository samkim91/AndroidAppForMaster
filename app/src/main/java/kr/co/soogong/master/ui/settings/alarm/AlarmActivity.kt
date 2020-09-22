package kr.co.soogong.master.ui.settings.alarm

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAlarmBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

class AlarmActivity : BaseActivity<ActivityAlarmBinding>(
    R.layout.activity_alarm
) {

    val viewModel: AlarmViewModel by lazy {
        ViewModelProvider(this).get(AlarmViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@AlarmActivity
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