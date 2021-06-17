package kr.co.soogong.master.ui.requirement.action.end

import android.icu.util.Calendar
import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.EndEstimate
import kr.co.soogong.master.databinding.ActivityEndRepairBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.requirement.action.end.EndRepairViewModel.Companion.END_REPAIR_FAILED
import kr.co.soogong.master.ui.requirement.action.end.EndRepairViewModel.Companion.END_REPAIR_SUCCESSFULLY
import kr.co.soogong.master.uihelper.requirment.action.end.EndRepairActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@AndroidEntryPoint
class EndRepairActivity : BaseActivity<ActivityEndRepairBinding>(
    R.layout.activity_end_repair
) {
    val requirementId: Int by lazy {
        EndRepairActivityHelper.getRequirementId(intent)
    }
    private val viewModel: EndRepairViewModel by viewModels()

    private var actualDate = Calendar.getInstance()

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
                title.text = getString(R.string.end_estimate_title)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
                button.setOnClickListener {
                    if (amount.text.isNullOrEmpty() || amount.text.toString().replace(",", "").toLong() < 10000) {
                        amount.alertVisible = amount.text.isNullOrEmpty() || amount.text.toString().replace(",", "").toLong() < 10000

                        return@setOnClickListener
                    }

                    viewModel.endRepair(
                        estimationId = requirementId,
                        endEstimate = EndEstimate(
                            actualPrice = amount.text,
                            actualDate = SimpleDateFormat("yyyy-MM-dd").format(actualDate.time)
                        )
                    )
                }
            }

            calender.setOnDateChangeListener { _: CalendarView, year: Int, month: Int, day: Int ->
                actualDate.set(year, month, day)
            }

            amount.addTextChangedListener(afterTextChanged = {
                amount.alertVisible =
                    amount.text.isNullOrEmpty() || amount.text.toString().replace(",", "")
                        .toLong() < 10000
            })

            amount.addFocusChangeListener(onFocusChange = { _, hasFocus ->
                val amountData = amount.text.toString().replace(",", "")
                if (hasFocus) amount.text = amountData
                else if (!amountData.isNullOrEmpty()) amount.text =
                    "${DecimalFormat("#,###").format(amountData.toLong())}"
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
                END_REPAIR_FAILED -> {
                    toast(getString(R.string.send_message_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EndRepairActivity"
    }
}