package kr.co.soogong.master.ui.requirement.action.write

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.global.ColorTheme
import kr.co.soogong.master.databinding.ActivityMeasurementBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.base.BaseViewModel.Companion.SHOW_LOADING
import kr.co.soogong.master.ui.dialog.popup.DefaultDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.SEND_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.uihelper.requirment.action.EstimationTemplatesActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.isIntRange
import kr.co.soogong.master.utility.extension.toast
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
            colorThemeEstimationTemplate = ColorTheme.Green

            abHeader.setButtonBackClickListener { onBackPressed() }

            bSendEstimation.setOnClickListener {
                validateCost()
                if (stiEstimationCost.error.isNullOrEmpty()) viewModel.sendEstimation()
            }

            saidAttachments.setImagesDeletableAdapter { viewModel.estimationImages.removeAt(it) }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this@MeasurementActivity, EventObserver { event ->
            when (event) {
                WriteEstimationViewModel.START_ESTIMATION_TEMPLATE -> estimationTemplateLauncher.launch(
                    EstimationTemplatesActivityHelper.getIntent(this))
                WriteEstimationViewModel.START_IMAGE_PICKER -> {
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
                WriteEstimationViewModel.START_VIEW_REQUIREMENT -> viewModel.requirement.value?.let {
                    startActivity(ViewRequirementActivityHelper.getIntent(this, it.id))
                }
                SEND_ESTIMATION_SUCCESSFULLY -> {
                    toast(getString(R.string.send_message_succeeded))
                    super.onBackPressed()
                }
                REQUEST_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
                SHOW_LOADING -> showLoading(supportFragmentManager)
            }
        })
    }

    private fun validateCost() {
        Timber.tag(TAG).d("registerCostObserver: ")
        with(binding) {
            viewModel.simpleCost.value.let {
                stiEstimationCost.error = when {
                    it == null || it < 10000 -> getString(R.string.minimum_cost)
                    !it.isIntRange() -> getString(R.string.too_large_number)
                    else -> null
                }
            }
        }
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
        private const val TAG = "MeasurementActivity"
    }
}