package kr.co.soogong.master.ui.requirement.action.write

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.model.requirement.estimation.EstimationTypeCode
import kr.co.soogong.master.databinding.ActivityWriteEstimationBinding
import kr.co.soogong.master.atomic.molecules.IconLabelContainer
import kr.co.soogong.master.atomic.molecules.IconLabelContainer.Companion.PREVIOUS_ESTIMATION_TYPE
import kr.co.soogong.master.atomic.molecules.IconLabelContainer.Companion.REQUIREMENT_TYPE
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getCancelSendingEstimationDialogData
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.SEND_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.uihelper.requirment.action.EstimationTemplatesActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.formatComma
import kr.co.soogong.master.utility.extension.toast
import kr.co.soogong.master.utility.validation.ValidationHelper
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

            with(actionBar) {
                title.text = getString(R.string.write_estimate_title)
                backButton.setOnClickListener {
                    customBackPressed()
                }

                button.text = getString(R.string.send_estimation)
                button.setOnClickListener {
                    registerCostsObserve()
                    if (viewModel.estimationType.value == EstimationTypeCode.INTEGRATION) {
                        if (!simpleCost.alertVisible && ValidationHelper.isIntRange(viewModel.simpleCost.value!!)) {
                            showLoading(supportFragmentManager)
                            viewModel.sendEstimation()
                        }
                    } else {
                        if ((!laborCost.alertVisible && !materialCost.alertVisible && !travelCost.alertVisible) && ValidationHelper.isIntRange(
                                viewModel.totalCost.value!!)
                        ) {
                            showLoading(supportFragmentManager)
                            viewModel.sendEstimation()
                        }
                    }
                }
            }

            requestTypeGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    filterOption1.id -> {
                        estimationIntegration.visibility = View.VISIBLE
                        estimationByItemGroup.visibility = View.GONE
                        viewModel.estimationType.value = EstimationTypeCode.INTEGRATION
                    }

                    filterOption2.id -> {
                        estimationIntegration.visibility = View.GONE
                        estimationByItemGroup.visibility = View.VISIBLE
                        viewModel.estimationType.value = EstimationTypeCode.BY_ITEM
                    }
                }
            }

            simpleCost.addFocusChangeListener(onFocusChange = { _, hasFocus ->
                if (!viewModel.simpleCost.value.isNullOrEmpty()) {     // ?. 이 제대로 작동하지 않는다.
                    viewModel.simpleCost.value?.replace(",", "").let {
                        if (hasFocus) {
                            viewModel.simpleCost.value = it
                        } else {
                            viewModel.simpleCost.value = it?.toLong().formatComma()
                        }
                    }
                }
            })

            totalCost.setEditTextBackground(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.shape_gray_background_gray_border_radius50,
                    null
                )
            )

            checkboxForSimpleCostIncludingVat.checkBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.includingVat.value = isChecked
            }

            checkboxForTotalCostIncludingVat.checkBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.includingVat.value = isChecked
            }

            alertBoxForLoadingEstimationTemplate.setOnClickListener {
                estimationTemplateLauncher.launch(EstimationTemplatesActivityHelper.getIntent(this@WriteEstimationActivity))
            }

            checkboxForAddingEstimationTemplate.checkBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.isSavingTemplate.value = isChecked
            }

            estimationImagesPicker.setAdapter { viewModel.estimationImages.removeAt(it) }

            estimationImagesPicker.addIconClickListener {
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

            // TODO: 2021/08/25 화면 열릴 때 키보드가 나오고 포커스 되는것 까지 구현 필요
//            if(simpleCost.requestFocus()) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
//            if (scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
//            )
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.laborCost.observe(this@WriteEstimationActivity, {
            setTotalAmount()
        })

        viewModel.materialCost.observe(this@WriteEstimationActivity, {
            setTotalAmount()
        })

        viewModel.travelCost.observe(this@WriteEstimationActivity, {
            setTotalAmount()
        })

        viewModel.action.observe(this@WriteEstimationActivity, EventObserver { event ->
            dismissLoading()
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

        viewModel.requirement.observe(this, { requirement ->
            binding.flexibleContainer.removeAllViews()
            // view : 고객 요청 내용, 이전 실측 내용(있으면)
            IconLabelContainer.addIconLabelContainer(
                context = this,
                container = binding.flexibleContainer,
                requirementDto = requirement,
                contentType = REQUIREMENT_TYPE,
            )
            requirement.measurement?.let {
                IconLabelContainer.addIconLabelContainer(
                    context = this,
                    container = binding.flexibleContainer,
                    requirementDto = requirement,
                    contentType = PREVIOUS_ESTIMATION_TYPE,
                )
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestRequirement()
    }

    private fun registerCostsObserve() {
        Timber.tag(TAG).d("registerCostsObserve: ")
        bind {
            viewModel.simpleCost.observe(this@WriteEstimationActivity, {
                simpleCost.alertVisible =
                    simpleCost.text.isNullOrEmpty() || simpleCost.text.toString().replace(",", "")
                        .toLong() < 10000
            })

            viewModel.laborCost.observe(this@WriteEstimationActivity, {
                laborCost.alertVisible = laborCost.text.isNullOrEmpty()
            })

            viewModel.materialCost.observe(this@WriteEstimationActivity, {
                materialCost.alertVisible = materialCost.text.isNullOrEmpty()
            })

            viewModel.travelCost.observe(this@WriteEstimationActivity, {
                travelCost.alertVisible = travelCost.text.isNullOrEmpty()
            })
        }
    }

    private fun setTotalAmount() {
        with(viewModel) {
            val laborCostInt =
                if (laborCost.value.isNullOrEmpty()) 0 else laborCost.value!!.toLong()
            val materialCostInt =
                if (materialCost.value.isNullOrEmpty()) 0 else materialCost.value!!.toLong()
            val travelCostInt =
                if (travelCost.value.isNullOrEmpty()) 0 else travelCost.value!!.toLong()

            totalCost.value = (laborCostInt + materialCostInt + travelCostInt).formatComma()
        }
    }

    override fun onBackPressed() {
        customBackPressed()
    }

    private fun customBackPressed() {
        CustomDialog.newInstance(
            getCancelSendingEstimationDialogData(this@WriteEstimationActivity)
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
        private const val TAG = "WriteEstimationActivity"
    }
}