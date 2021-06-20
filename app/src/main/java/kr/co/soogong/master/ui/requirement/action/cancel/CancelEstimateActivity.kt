package kr.co.soogong.master.ui.requirement.action.cancel

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.CancelEstimate
import kr.co.soogong.master.databinding.ActivityCancelEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.requirement.action.cancel.CancelEstimateViewModel.Companion.CANCEL_ESTIMATE_FAILED
import kr.co.soogong.master.ui.requirement.action.cancel.CancelEstimateViewModel.Companion.CANCEL_ESTIMATE_SUCCEEDED
import kr.co.soogong.master.uihelper.requirment.action.CancelEstimationActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class CancelEstimateActivity : BaseActivity<ActivityCancelEstimateBinding>(
    R.layout.activity_cancel_estimate
) {
    val estimationId: Int by lazy {
        CancelEstimationActivityHelper.getRequirementId(intent)
    }

    private val viewModel: CancelEstimateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel

            lifecycleOwner = this@CancelEstimateActivity

            with(actionBar) {
                title.text = getString(R.string.cancel_estimate_title)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }

                button.setOnClickListener {
                    viewModel.doCancel(
                        estimationId = estimationId, cancelEstimate = CancelEstimate(
                            message = when (binding.cancelOption.checkedRadioButtonId) {
                                R.id.cancel_option_1 -> getString(R.string.cancel_estimate_cancel_option_1)
                                R.id.cancel_option_2 -> getString(R.string.cancel_estimate_cancel_option_2)
                                R.id.cancel_option_3 -> getString(R.string.cancel_estimate_cancel_option_3)
                                R.id.cancel_option_4 -> getString(R.string.cancel_estimate_cancel_option_4)
                                else -> {
                                    binding.alert.visibility = View.VISIBLE
                                    return@setOnClickListener
                                }
                            },
                            subMessage = binding.cancelOptionDetail.text
                        )
                    )
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(this@CancelEstimateActivity, EventObserver { event ->
            when (event) {
                CANCEL_ESTIMATE_SUCCEEDED -> {
                    toast(getString(R.string.cancel_estimate_succeeded))
                    super.onBackPressed()
                }
                CANCEL_ESTIMATE_FAILED -> {
                    toast(getString(R.string.send_message_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "CancelEstimateActivity"
    }

}