package kr.co.soogong.master.ui.requirements.action.end

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEndEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

@AndroidEntryPoint
class EndEstimateActivity : BaseActivity<ActivityEndEstimateBinding>(
    R.layout.activity_end_estimate
) {
    private val viewModel: EndEstimateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        bind {
            vm = viewModel

            with(actionBar) {
                title.text = getString(R.string.end_estimate_title)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
                button.setOnClickListener {
                    viewModel.doOnFinish()
                }
            }
        }
    }

    companion object {
        private const val TAG = "EndEstimateActivity"
    }
}