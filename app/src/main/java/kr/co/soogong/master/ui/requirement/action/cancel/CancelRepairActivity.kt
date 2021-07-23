package kr.co.soogong.master.ui.requirement.action.cancel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.repair.*
import kr.co.soogong.master.databinding.ActivityCancelRepairBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.requirement.action.cancel.CancelRepairViewModel.Companion.CANCEL_ESTIMATION_FAILED
import kr.co.soogong.master.ui.requirement.action.cancel.CancelRepairViewModel.Companion.CANCEL_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.uihelper.requirment.action.CancelRepairActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class CancelRepairActivity : BaseActivity<ActivityCancelRepairBinding>(
    R.layout.activity_cancel_repair
) {
    val requirementId: Int by lazy {
        CancelRepairActivityHelper.getRequirementId(intent)
    }

    private val viewModel: CancelRepairViewModel by viewModels()

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

            lifecycleOwner = this@CancelRepairActivity

            with(actionBar) {
                title.text = getString(R.string.cancel_estimate_title)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
                button.text = getString(R.string.writing_done)
                button.setOnClickListener {
                    viewModel.canceledCode.observe(this@CancelRepairActivity, {
                        alert.isVisible = it.isNullOrEmpty()
                    })

                    if (!alert.isVisible) viewModel.saveRepair()
                }

                cancelOption.setOnCheckedChangeListener { _, checkedId ->
                    viewModel.canceledCode.value = when (checkedId) {
                        cancelOption1.id -> ChangeOfMind.code
                        cancelOption2.id -> NoResponse.code
                        cancelOption3.id -> RepairImpossible.code
                        cancelOption4.id -> DifferentCost.code
                        else -> ""
                    }
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(this@CancelRepairActivity, EventObserver { event ->
            when (event) {
                CANCEL_ESTIMATION_SUCCESSFULLY -> {
                    toast(getString(R.string.cancel_estimate_succeeded))
                    super.onBackPressed()
                }
                CANCEL_ESTIMATION_FAILED -> {
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
        private const val TAG = "CancelEstimationActivity"
    }

}