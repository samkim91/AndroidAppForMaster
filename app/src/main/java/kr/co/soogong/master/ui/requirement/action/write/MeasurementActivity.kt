package kr.co.soogong.master.ui.requirement.action.write

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.databinding.ActivityMeasurementBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.base.BaseViewModel.Companion.DISMISS_LOADING
import kr.co.soogong.master.ui.base.BaseViewModel.Companion.SHOW_LOADING
import kr.co.soogong.master.ui.dialog.popup.DefaultDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.SEND_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.uihelper.requirment.action.EstimationTemplatesActivityHelper
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

            with(actionBar) {
                title.text = getString(R.string.write_estimate_title)
                backButton.setOnClickListener {
                    onBackPressed()
                }

                button.text = getString(R.string.send_estimation)
                button.setOnClickListener {
                    registerCostObserver()
                    if (!simpleCost.alertVisible && ValidationHelper.isIntRange(viewModel.simpleCost.value!!)) {
                        viewModel.sendEstimation()
                    }
                }
            }

            checkboxForSimpleCostIncludingVat.checkBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.includingVat.value = isChecked
            }

            measurementImagesPicker.setAdapter { viewModel.estimationImages.removeAt(it) }

            measurementImagesPicker.addIconClickListener {
                PermissionHelper.checkImagePermission(context = this@MeasurementActivity,
                    onGranted = {
                        TedImagePicker.with(this@MeasurementActivity)
                            .buttonBackground(R.drawable.shape_green_background_radius8)
                            .max(
                                (10 - viewModel.estimationImages.getItemCount()),
                                resources.getString(R.string.maximum_images_count)
                            )
                            .startMultiImage { uriList ->
                                if (FileHelper.isImageExtension(uriList,
                                        this@MeasurementActivity) == false
                                ) {
                                    toast(getString(R.string.invalid_image_extension))
                                    return@startMultiImage
                                }

                                viewModel.estimationImages.addAll(uriList.map {
                                    AttachmentDto(
                                        id = null,
                                        partOf = null,
                                        referenceId = null,
                                        description = null,
                                        s3Name = null,
                                        fileName = null,
                                        url = null,
                                        uri = it,
                                    )
                                })
                            }
                    },
                    onDenied = { })
            }

            alertBoxForLoadingEstimationTemplate.setOnClickListener {
                estimationTemplateLauncher.launch(EstimationTemplatesActivityHelper.getIntent(this@MeasurementActivity))
            }

            checkboxForAddingEstimationTemplate.setCheckClick {
                viewModel.isSavingTemplate.value =
                    checkboxForAddingEstimationTemplate.checkBox.isChecked
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this@MeasurementActivity, EventObserver { event ->
            dismissLoading()
            when (event) {
                SEND_ESTIMATION_SUCCESSFULLY -> {
                    toast(getString(R.string.send_message_succeeded))
                    super.onBackPressed()
                }
                REQUEST_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
                SHOW_LOADING -> showLoading(supportFragmentManager)
                DISMISS_LOADING -> dismissLoading()
            }
        })
    }

    private fun registerCostObserver() {
        Timber.tag(TAG).d("registerCostObserver: ")
        bind {
            viewModel.simpleCost.observe(this@MeasurementActivity, {
                simpleCost.alertVisible =
                    simpleCost.text.isNullOrEmpty() || simpleCost.text.toString().replace(",", "")
                        .toLong() < 10000
            })
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestRequirement()
    }

    override fun onBackPressed() {
        customBackPressed()
    }

    private fun customBackPressed() {
        DefaultDialog.newInstance(
            DialogData.getCancelSendingEstimationDialogData()
        ).let {
            it.setButtonsClickListener(
                onPositive = {
                    finish()
                },
                onNegative = { }
            )
            it.show(supportFragmentManager, it.tag)
        }
    }

    companion object {
        private const val TAG = "MeasurementActivity"
    }
}