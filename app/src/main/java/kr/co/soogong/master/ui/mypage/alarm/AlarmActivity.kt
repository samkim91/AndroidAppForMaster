package kr.co.soogong.master.ui.mypage.alarm

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

    private val viewModel: AlarmViewModel by lazy {
        ViewModelProvider(this).get(AlarmViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        viewModel.getAlarmStatus()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            setVariable(BR.vm, viewModel)
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