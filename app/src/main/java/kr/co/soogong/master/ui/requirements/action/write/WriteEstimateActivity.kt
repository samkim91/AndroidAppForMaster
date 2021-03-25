package kr.co.soogong.master.ui.requirements.action.write

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityWriteEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

@AndroidEntryPoint
class WriteEstimateActivity : BaseActivity<ActivityWriteEstimateBinding>(
    R.layout.activity_write_estimate
) {
    private val viewModel: WriteEstimateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        bind {
            vm = viewModel

            with(actionBar) {
                title.text = getString(R.string.write_estimate_title)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
                button.setOnClickListener {
                    viewModel.doOnWrite()
                }
            }

            requestTypeGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    filterOption1.id -> {
                        amount.visibility = View.VISIBLE
                        requestDetail.visibility = View.GONE
                    }

                    filterOption2.id -> {
                        amount.visibility = View.GONE
                        requestDetail.visibility = View.VISIBLE
                    }
                }
            }

        }
    }

    companion object {
        private const val TAG = "WriteEstimateActivity"
    }
}