package kr.co.soogong.master.ui.requirement.action.write

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.estimation.EstimationTypeCode
import kr.co.soogong.master.databinding.ActivityWriteEstimationBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getCancelSendingEstimationDialogData
import kr.co.soogong.master.ui.image.RectangleImageAdapter
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.SEND_MESSAGE_FAILED
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimationViewModel.Companion.SEND_ESTIMATION_SUCCESSFULLY
import kr.co.soogong.master.uihelper.image.ImageViewActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.WriteEstimationActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.addAdditionInfoView
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class WriteEstimationActivity : BaseActivity<ActivityWriteEstimationBinding>(
    R.layout.activity_write_estimation
) {
    private val requirementId: Int by lazy {
        WriteEstimationActivityHelper.getEstimationId(intent)
    }

    // TODO.. custom widget 의 2way binding 을 적용해야함.
    private val viewModel: WriteEstimationViewModel by viewModels()

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
                    val dialog = CustomDialog(
                        getCancelSendingEstimationDialogData(this@WriteEstimationActivity),
                        yesClick = {
                            super.onBackPressed()
                        },
                        noClick = { }
                    )

                    dialog.show(supportFragmentManager, dialog.tag)
                }


                button.text = getString(R.string.send_estimation)
                button.setOnClickListener {
                    registerCostsObserve()
                    if (viewModel.estimationType.value == EstimationTypeCode.INTEGRATION && simpleCost.alertVisible) return@setOnClickListener
                    if (viewModel.estimationType.value == EstimationTypeCode.BY_ITEM && (laborCost.alertVisible || materialCost.alertVisible || travelCost.alertVisible)) return@setOnClickListener

                    viewModel.sendEstimation()
                }
            }

            detailButton.setOnClickListener() {
                if (detailContainer.isVisible) {
                    detailContainer.visibility = View.GONE
                    detailIcon.setImageResource(R.drawable.ic_arrow_down)
                } else {
                    detailContainer.visibility = View.VISIBLE
                    detailIcon.setImageResource(R.drawable.ic_arrow_up)
                }
            }

            photoList.adapter = RectangleImageAdapter(
                cardClickListener = { position ->
                    startActivity(
                        ImageViewActivityHelper.getIntent(
                            this@WriteEstimationActivity,
                            viewModel.requirement.value?.images,
                            position
                        )
                    )
                }
            )

            requestTypeGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    filterOption1.id -> {
                        simpleCost.visibility = View.VISIBLE
                        estimationByItemGroup.visibility = View.GONE
                        viewModel.estimationType.value = EstimationTypeCode.INTEGRATION
                    }

                    filterOption2.id -> {
                        simpleCost.visibility = View.GONE
                        estimationByItemGroup.visibility = View.VISIBLE
                        viewModel.estimationType.value = EstimationTypeCode.BY_ITEM
                        totalCost.setEditTextBackground(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.shape_fill_gray_background_gray_border,
                                null
                            )
                        )
                    }
                }
            }

            simpleCost.addFocusChangeListener(onFocusChange = { _, hasFocus ->
                if (!viewModel.simpleCost.value.isNullOrEmpty()) {     // ?. 이 제대로 작동하지 않는다.
                    viewModel.simpleCost.value?.replace(",", "").let {
                        if (hasFocus) {
                            viewModel.simpleCost.value = it
                        } else {
                            viewModel.simpleCost.value = DecimalFormat("#,###").format(it?.toLong())
                        }
                    }
                }
            })
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

        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                SEND_ESTIMATION_SUCCESSFULLY -> {
                    toast(getString(R.string.send_message_succeeded))
                    super.onBackPressed()
                }
                SEND_MESSAGE_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })

        viewModel.requirement.observe(this, { requirement ->
            // TODO: 2021/06/16 고객 요청내용 추가되면 바꿔줘야함
            val requirementQnas = requirement?.requirementQnas
            if (!requirementQnas.isNullOrEmpty()) addAdditionInfoView(binding.customFrame, this, requirementQnas)
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

            totalCost.value =
                DecimalFormat("#,###").format(laborCostInt + materialCostInt + travelCostInt)
        }
    }

    companion object {
        private const val TAG = "WriteEstimationActivity"
    }
}