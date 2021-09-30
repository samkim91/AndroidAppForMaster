package kr.co.soogong.master.ui.requirement.action.cancel

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.CompareCodeTable
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.data.model.requirement.repair.*
import kr.co.soogong.master.databinding.ActivityCancelBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.requirement.action.cancel.CancelViewModel.Companion.CANCEL_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.cancel.CancelViewModel.Companion.CANCEL_MEASURE_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.cancel.CancelViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class CancelActivity : BaseActivity<ActivityCancelBinding>(
    R.layout.activity_cancel
) {
    private val viewModel: CancelViewModel by viewModels()

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

            lifecycleOwner = this@CancelActivity

            with(actionBar) {
                title.text = getString(R.string.cancel_estimate_title)

                backButton.setOnClickListener {
                    super.onBackPressed()
                }

                button.text = getString(R.string.writing_done)
                button.setOnClickListener {
                    viewModel.canceledCode.observe(this@CancelActivity, {
                        canceledOptions.alertVisible = it.isNullOrEmpty()
                        cancelOptionDetail.alertVisible = it == CanceledReasonRadioGroupHelper.canceledReasons.last().code
                    })

                    viewModel.canceledDescription.observe(this@CancelActivity, {
                        cancelOptionDetail.alertVisible =
                            viewModel.canceledCode.value == CanceledReasonRadioGroupHelper.canceledReasons.last().code && it.length < 10
                    })

                    if (canceledOptions.alertVisible || cancelOptionDetail.alertVisible) return@setOnClickListener

                    if (viewModel.requirement.value?.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED) viewModel.saveRepair() else viewModel.respondToMeasure()
                }

                CanceledReasonRadioGroupHelper(this@CancelActivity, canceledOptions)

                canceledOptions.addCheckedChangeListener { group, checkedId ->
                    group.indexOfChild(group.findViewById<RadioButton>(checkedId)).let {
                        viewModel.canceledCode.value =
                            CanceledReasonRadioGroupHelper.canceledReasons[it].code
                    }
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this@CancelActivity, EventObserver { event ->
            when (event) {
                CANCEL_ESTIMATION_SUCCESSFULLY -> {
                    toast(getString(R.string.cancel_estimate_succeeded))
                    super.onBackPressed()
                }
                CANCEL_MEASURE_SUCCESSFULLY -> {
                    toast(getString(R.string.refuse_to_estimate_or_measure_successfully_text))
                    super.onBackPressed()
                }
                REQUEST_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestRequirement()
    }

    companion object {
        private const val TAG = "CancelActivity"
    }

}