package kr.co.soogong.master.presentation.ui.requirement.action.write

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.presentation.atomic.molecules.SubheadlineChipGroup
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.common.ColorTheme
import kr.co.soogong.master.databinding.ActivityWriteEstimationBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.SHOW_LOADING
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.requirement.action.write.WriteEstimationViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.requirement.action.write.WriteEstimationViewModel.Companion.SEND_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.requirement.action.write.WriteEstimationViewModel.Companion.START_ESTIMATION_TEMPLATE
import kr.co.soogong.master.presentation.ui.requirement.action.write.WriteEstimationViewModel.Companion.START_IMAGE_PICKER
import kr.co.soogong.master.presentation.ui.requirement.action.write.WriteEstimationViewModel.Companion.START_VIEW_REQUIREMENT
import kr.co.soogong.master.presentation.uihelper.requirment.action.EstimationTemplatesActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.isIntRange
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class WriteEstimationActivity : BaseActivity<ActivityWriteEstimationBinding>(
    R.layout.activity_write_estimation
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
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            vm = viewModel
            lifecycleOwner = this@WriteEstimationActivity
            colorThemeEstimationTemplate = ColorTheme.Green

            initChipGroup()

            abHeader.setIvBackClickListener { onBackPressed() }

            saidAttachments.setImagesDeletableAdapter { viewModel.estimationImages.removeAt(it) }

            bSendEstimation.setOnClickListener {

                if (viewModel.estimationType.value == CodeTable.INTEGRATION) {
                    validateSimpleCost()
                    if (stiEstimationCost.error.isNullOrEmpty()) viewModel.sendEstimation()
                } else {
                    validateTotalCost()
                    if (stiLaborCost.error.isNullOrEmpty() && stiMaterialCost.error.isNullOrEmpty() && stiTravelCost.error.isNullOrEmpty() && stiTotalCost.error.isNullOrEmpty()) viewModel.sendEstimation()
                }
            }

            // TODO: 2021/08/25 화면 열릴 때 키보드가 나오고 포커스 되는것 까지 구현 필요
//            if(simpleCost.requestFocus()) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
//            if (scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
//            )
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.laborCost.observe(this@WriteEstimationActivity) {
            setTotalAmount()
        }

        viewModel.materialCost.observe(this@WriteEstimationActivity) {
            setTotalAmount()
        }

        viewModel.travelCost.observe(this@WriteEstimationActivity) {
            setTotalAmount()
        }

        viewModel.action.observe(this@WriteEstimationActivity, EventObserver { event ->
            when (event) {
                START_ESTIMATION_TEMPLATE -> estimationTemplateLauncher.launch(
                    EstimationTemplatesActivityHelper.getIntent(this@WriteEstimationActivity))
                START_IMAGE_PICKER -> {
                    PermissionHelper.checkImagePermission(context = this@WriteEstimationActivity,
                        onGranted = {
                            TedImagePicker.with(this@WriteEstimationActivity)
                                .buttonBackground(R.drawable.shape_green_background_radius8)
                                .max(
                                    (10 - viewModel.estimationImages.getItemCount()),
                                    resources.getString(R.string.maximum_images_count)
                                )
                                .startMultiImage { uriList ->
                                    if (FileHelper.isImageExtension(
                                            uriList,
                                            this@WriteEstimationActivity
                                        ) == false
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
                START_VIEW_REQUIREMENT -> viewModel.requirement.value?.let {
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

    private fun initChipGroup() {
        SubheadlineChipGroup.initChoiceRectangularChips(
            this,
            layoutInflater,
            binding.scgEstimationType,
            viewModel.estimationTypes.map { it.inKorean }
        )
    }

    private fun validateSimpleCost() {
        Timber.tag(TAG).d("validateSimpleCost: ")
        with(binding) {
            viewModel.simpleCost.value.let {
                stiEstimationCost.error = when {
                    it == null || it < 10000L -> getString(R.string.minimum_cost)
                    !it.isIntRange() -> getString(R.string.too_large_number)
                    else -> null
                }
            }
        }
    }

    private fun validateTotalCost() {
        Timber.tag(TAG).d("validateTotalCost: ")
        with(binding) {
            viewModel.laborCost.value.let {
                stiLaborCost.error = when {
                    it == null || it == 0L -> getString(R.string.required_field_alert)
                    !it.isIntRange() -> getString(R.string.too_large_number)
                    else -> null
                }
            }

            viewModel.materialCost.value.let {
                stiMaterialCost.error = when {
                    it == null || it == 0L -> getString(R.string.required_field_alert)
                    !it.isIntRange() -> getString(R.string.too_large_number)
                    else -> null
                }
            }

            viewModel.travelCost.value.let {
                stiTravelCost.error = when {
                    it == null || it == 0L -> getString(R.string.required_field_alert)
                    !it.isIntRange() -> getString(R.string.too_large_number)
                    else -> null
                }
            }

            viewModel.totalCost.value.let {
                stiTotalCost.error = when {
                    it == null || it < 10000 -> getString(R.string.minimum_cost)
                    !it.isIntRange() -> getString(R.string.too_large_number)
                    else -> null
                }
            }
        }
    }

    private fun setTotalAmount() {
        with(viewModel) {
            totalCost.value = laborCost.value!! + materialCost.value!! + travelCost.value!!
        }
    }

    override fun onBackPressed() {
        customBackPressed()
    }

    private fun customBackPressed() {
        // 견적 작성 그만둘지 확인 다이얼로그 전시
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
        private const val TAG = "WriteEstimationActivity"
    }
}