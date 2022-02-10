package kr.co.soogong.master.presentation.ui.requirement.action.cancel

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityCancelBinding
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.presentation.atomic.molecules.SubheadlineRadioGroup
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.requirement.action.cancel.CancelViewModel.Companion.CANCEL_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.requirement.action.cancel.CancelViewModel.Companion.CANCEL_MEASURE_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.requirement.action.cancel.CancelViewModel.Companion.REQUEST_FAILED
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

            abHeader.setIvBackClickListener { onBackPressed() }

            bCancel.setOnClickListener {
                viewModel.canceledCode.observe(this@CancelActivity, {
                    srgCanceledOptions.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                })

                viewModel.canceledDescription.observe(this@CancelActivity, {
                    stcDescription.error =
                        if (viewModel.canceledCode.value == viewModel.canceledReasons.value?.last()?.code && it.length < 10)
                            getString(R.string.fill_text_over_10)
                        else
                            null
                })

                if (!srgCanceledOptions.error.isNullOrEmpty() || !stcDescription.error.isNullOrEmpty()) return@setOnClickListener

                // 실측 요청 -> 거절
                // 이외(실측 예정, 실측 완료, 시공 예정 등) -> 시공 취소
                if (viewModel.requirement.value?.status is RequirementStatus.RequestMeasure) viewModel.respondToMeasure() else viewModel.saveRepair()
            }

            srgCanceledOptions.addCheckedChangeListener { group, checkedId ->
                group.indexOfChild(group.findViewById<RadioButton>(checkedId)).let { position ->
                    viewModel.canceledCode.value =
                        viewModel.canceledReasons.value?.get(position)?.code
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

        viewModel.canceledReasons.observe(this, { reasons ->
            // 취소사유 바인딩
            SubheadlineRadioGroup.addRadioButtons(
                this,
                binding.srgCanceledOptions.radioGroup,
                reasons.map { it.name }
            )
        })
    }

    companion object {
        private const val TAG = "CancelActivity"
    }

}