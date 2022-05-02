package kr.co.soogong.master.presentation.ui.requirement.action.write

import android.app.Activity
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityMeasurementBinding
import kr.co.soogong.master.domain.entity.common.ColorTheme
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.SHOW_LOADING
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.requirement.action.write.MeasurementViewModel.Companion.UPDATE_VISITING_DATE_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.requirement.action.write.MeasurementViewModel.Companion.START_DATE_PICKER
import kr.co.soogong.master.presentation.ui.requirement.action.write.MeasurementViewModel.Companion.START_TIME_PICKER
import kr.co.soogong.master.presentation.uihelper.requirment.action.EstimationTemplatesActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class MeasurementActivity : BaseActivity<ActivityMeasurementBinding>(
    R.layout.activity_measurement
) {
    private val viewModel: MeasurementViewModel by viewModels()

    private val estimationTemplateLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.description.value =
                    EstimationTemplatesActivityHelper.getResponse(result.data!!)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@MeasurementActivity
            vm = viewModel
            colorThemeEstimationTemplate = ColorTheme.Green

            abHeader.setIvBackClickListener { onBackPressed() }

            bSendEstimation.setOnClickListener {
                tilContainerDate.error =
                    if (viewModel.date.value == null) getString(R.string.required_field_alert) else null

                tilContainerTime.error =
                    if (viewModel.time.value == null) getString(R.string.required_field_alert) else null

                if (tilContainerTime.error.isNullOrEmpty() && tieEdittextTime.error.isNullOrEmpty()) viewModel.updateVisitingDate()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this@MeasurementActivity, EventObserver { event ->
            when (event) {
                MeasurementViewModel.START_ESTIMATION_TEMPLATE -> estimationTemplateLauncher.launch(
                    EstimationTemplatesActivityHelper.getIntent(this))
                MeasurementViewModel.START_VIEW_REQUIREMENT -> viewModel.requirement.value?.let {
                    startActivity(ViewRequirementActivityHelper.getIntent(this, it.id))
                }
                UPDATE_VISITING_DATE_SUCCESSFULLY -> super.onBackPressed()
                START_DATE_PICKER -> showDatePicker()
                START_TIME_PICKER -> showTimePicker()
                REQUEST_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
                SHOW_LOADING -> showLoading(supportFragmentManager)
            }
        })
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(
                CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build()
            )
            .build()

        datePicker.addOnPositiveButtonClickListener {
            Calendar.getInstance(Locale.KOREA).run {
                timeInMillis = it
                viewModel.date.value = this

                Timber.tag(TAG).i("date: ${viewModel.date.value?.time}")
                showTimePicker()
            }
        }

        datePicker.show(supportFragmentManager, TAG)
    }

    private fun showTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            Calendar.getInstance(Locale.KOREA).run {
                set(Calendar.HOUR_OF_DAY, timePicker.hour)
                set(Calendar.MINUTE, timePicker.minute)

                viewModel.time.value = this

                Timber.tag(TAG).i("time: ${viewModel.time.value?.time}")
            }
        }

        timePicker.show(supportFragmentManager, TAG)
    }

    override fun onBackPressed() {
        customBackPressed()
    }

    private fun customBackPressed() {
        DefaultDialog.newInstance(
            DialogData.getCancelSendingEstimation()
        ).let {
            it.setButtonsClickListener(
                onPositive = {
                    super.onBackPressed()
                },
                onNegative = { }
            )
            it.show(supportFragmentManager, it.tag)
        }
    }

    companion object {
        private val TAG = MeasurementActivity::class.java.simpleName
    }
}