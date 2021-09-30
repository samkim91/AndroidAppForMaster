package kr.co.soogong.master.ui.requirement.action.end

import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEndRepairBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.requirement.action.end.EndRepairViewModel.Companion.END_REPAIR_FAILED
import kr.co.soogong.master.ui.requirement.action.end.EndRepairViewModel.Companion.END_REPAIR_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.end.EndRepairViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber
import java.text.DecimalFormat
import java.util.*

@AndroidEntryPoint
class EndRepairActivity : BaseActivity<ActivityEndRepairBinding>(
    R.layout.activity_end_repair
) {
    private val viewModel: EndRepairViewModel by viewModels()

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
            lifecycleOwner = this@EndRepairActivity

            with(actionBar) {
                title.text = getString(R.string.end_repair_title)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
                button.text = getString(R.string.writing_done)
                button.setOnClickListener {
                    viewModel.actualPrice.observe(this@EndRepairActivity, {
                        actualPrice.alertVisible =
                            it.isNullOrEmpty() || it.replace(",", "").toLong() < 10000
                    })

                    if (!actualPrice.alertVisible && ValidationHelper.isIntRange(viewModel.actualPrice.value!!)) {
                        viewModel.saveRepair()
                    }
                }
            }

            calender.setOnDateChangeListener { _: CalendarView, year: Int, month: Int, day: Int ->
                Timber.tag(TAG).d("setOnDateChangeListener: ${year - month - day}")
                viewModel.actualDate.value?.set(year, month, day)
            }

            actualPrice.addFocusChangeListener(onFocusChange = { _, hasFocus ->
                if (!viewModel.actualPrice.value.isNullOrEmpty()) {
                    viewModel.actualPrice.value?.replace(",", "").let {
                        if (hasFocus) {
                            viewModel.actualPrice.value = it
                        } else {
                            viewModel.actualPrice.value =
                                DecimalFormat("#,###").format(it?.toLong())
                        }
                    }
                }
            })
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(this@EndRepairActivity, EventObserver { event ->
            when (event) {
                END_REPAIR_SUCCESSFULLY -> {
                    toast(getString(R.string.end_estimate_succeeded))
                    super.onBackPressed()
                }
                REQUEST_FAILED, END_REPAIR_FAILED -> {
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
        private const val TAG = "EndRepairActivity"
    }
}