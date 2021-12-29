package kr.co.soogong.master.ui.requirement.action.end

import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEndRepairBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.requirement.action.end.EndRepairViewModel.Companion.END_REPAIR_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.end.EndRepairViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.exceptComma
import kr.co.soogong.master.utility.extension.toast
import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber
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

            abHeader.setButtonBackClickListener { onBackPressed() }

//            stiActualPrice.setOnFocusChangeListener { v, hasFocus ->
//                if (!viewModel.actualPrice.value.isNullOrEmpty()) {
//                    if (hasFocus) {
//                        viewModel.actualPrice.value = viewModel.actualPrice.value?.replace(",", "")
//                    } else {
//                        viewModel.actualPrice.value =
//                            viewModel.actualPrice.value?.toLong().formatComma()
//                    }
//                }


            cvCalender.setOnDateChangeListener { _: CalendarView, year: Int, month: Int, day: Int ->
                Timber.tag(TAG).d("setOnDateChangeListener: ${year - month - day}")
                viewModel.actualDate.value?.set(year, month, day)
            }

            bEndRepair.setOnClickListener {
                viewModel.actualPrice.observe(this@EndRepairActivity, {
                    stiActualPrice.error = when {
                        it.exceptComma().toLong() < 10000 -> getString(R.string.minimum_cost)
                        !ValidationHelper.isIntRange(it) -> getString(R.string.too_large_number)
                        else -> null
                    }
                })

                if (stiActualPrice.error.isNullOrEmpty()) viewModel.saveRepair()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(this@EndRepairActivity, EventObserver { event ->
            when (event) {
                END_REPAIR_SUCCESSFULLY -> {
                    toast(getString(R.string.end_estimate_succeeded))
                    onBackPressed()
                }
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EndRepairActivity"
    }
}