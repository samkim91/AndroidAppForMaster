package kr.co.soogong.master.ui.requirements.action.cancel

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityCancelEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

@AndroidEntryPoint
class CancelEstimateActivity : BaseActivity<ActivityCancelEstimateBinding>(
    R.layout.activity_cancel_estimate
) {
    private val viewModel: CancelEstimateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        bind {
            vm = viewModel

            lifecycleOwner = this@CancelEstimateActivity

            with(actionBar) {
                title.text = getString(R.string.cancel_estimate_title)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
                button.setOnClickListener {
                    viewModel.doCancel()
                }
            }
        }
    }

    companion object {
        private const val TAG = "CancelEstimateActivity"
    }

}