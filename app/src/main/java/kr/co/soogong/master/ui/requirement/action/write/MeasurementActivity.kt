package kr.co.soogong.master.ui.requirement.action.write

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityMeasurementBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.SEND_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.toast
import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber

@AndroidEntryPoint
class MeasurementActivity : BaseActivity<ActivityMeasurementBinding>(
    R.layout.activity_measurement
) {
    private val viewModel: WriteEstimationViewModel by viewModels()

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

            with(actionBar) {
                title.text = getString(R.string.write_estimate_title)
                backButton.setOnClickListener {
                    onBackPressed()
                }

                cameraIconForMeasurementImage.setOnClickListener {
                    PermissionHelper.checkImagePermission(context = this@MeasurementActivity,
                        onGranted = {
                            TedImagePicker.with(this@MeasurementActivity)
                                .buttonBackground(R.drawable.shape_fill_green_background)
                                .start { uri ->
                                    if (FileHelper.isImageExtension(
                                            uri,
                                            this@MeasurementActivity
                                        ) == false
                                    ) {
                                        toast(getString(R.string.invalid_image_extension))
                                        return@start
                                    }

                                    viewModel.measurementImage.value = uri
                                }
                        },
                        onDenied = { })
                }

                button.text = getString(R.string.send_estimation)
                button.setOnClickListener {
                    if (!simpleCost.alertVisible && ValidationHelper.isIntRange(viewModel.simpleCost.value!!)) viewModel.sendEstimation()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        bind {
            viewModel.simpleCost.observe(this@MeasurementActivity, {
                simpleCost.alertVisible =
                    simpleCost.text.isNullOrEmpty() || simpleCost.text.toString().replace(",", "")
                        .toLong() < 10000
            })
            viewModel.action.observe(this@MeasurementActivity, EventObserver { event ->
                when (event) {
                    SEND_ESTIMATION_SUCCESSFULLY -> {
                        toast(getString(R.string.send_message_succeeded))
                        super.onBackPressed()
                    }
                    REQUEST_FAILED -> {
                        toast(getString(R.string.error_message_of_request_failed))
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        customBackPressed()
    }

    private fun customBackPressed() {
        val dialog = CustomDialog.newInstance(
            DialogData.getCancelSendingEstimationDialogData(this),
            yesClick = {
                finish()
            },
            noClick = { }
        )

        dialog.show(supportFragmentManager, dialog.tag)
    }

    companion object {
        private const val TAG = "MeasurementActivity"
    }
}