package kr.co.soogong.master.ui.requirement.action.write

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.uihelper.image.ImageViewActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.write.WriteEstimateActivityHelper
import kr.co.soogong.master.data.model.requirement.EstimationMessage
import kr.co.soogong.master.databinding.ActivityWriteEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getCancelSendingEstimationDialogData
import kr.co.soogong.master.ui.image.RectangleImageAdapter
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimateViewModel.Companion.SEND_MESSAGE_FAILED
import kr.co.soogong.master.ui.requirement.action.write.WriteEstimateViewModel.Companion.SEND_MESSAGE_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.addAdditionInfoView
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class WriteEstimateActivity : BaseActivity<ActivityWriteEstimateBinding>(
    R.layout.activity_write_estimate
) {
    private val requirementId: Int by lazy {
        WriteEstimateActivityHelper.getEstimationId(intent)
    }

    // TODO.. custom widget의 2way binding을 적용해야함.

    private val viewModel: WriteEstimateViewModel by viewModels()

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
            lifecycleOwner = this@WriteEstimateActivity

            with(actionBar) {
                title.text = getString(R.string.write_estimate_title)
                backButton.setOnClickListener {
                    val dialog = CustomDialog(
                        getCancelSendingEstimationDialogData(this@WriteEstimateActivity),
                        yesClick = {
                            super.onBackPressed()
                        },
                        noClick = { }
                    )

                    dialog.show(supportFragmentManager, dialog.tag)
                }
                button.text = getString(R.string.send_estimation)
                button.setOnClickListener {
                    lateinit var priceInNumber: String

                    if (viewModel.transmissionType == "통합견적") {
                        if (amount.text.isNullOrEmpty() || amount.text.toString().replace(",", "")
                                .toLong() < 10000
                        ) {
                            amount.alertVisible =
                                amount.text.isNullOrEmpty() || amount.text.toString()
                                    .replace(",", "").toLong() < 10000

                            return@setOnClickListener
                        }
                        priceInNumber = amount.text.toString().replace(",", "")
                    } else {
                        if (laborCost.text.isNullOrEmpty() || materialCost.text.isNullOrEmpty() || travelCost.text.isNullOrEmpty()) {
                            laborCost.alertVisible = laborCost.text.isNullOrEmpty()
                            materialCost.alertVisible = materialCost.text.isNullOrEmpty()
                            travelCost.alertVisible = travelCost.text.isNullOrEmpty()

                            return@setOnClickListener
                        }

                        priceInNumber = totalAmount.text.toString().replace(",", "")
                    }

                    viewModel.sendEstimation(
                        estimationMessage = EstimationMessage(
                            totalPrice = priceInNumber,
                            personnel = laborCost.text ?: "",
                            material = materialCost.text ?: "",
                            trip = travelCost.text ?: "",
                            description = writeDetail.text ?: ""
                        )
                    )
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
                            this@WriteEstimateActivity,
                            viewModel.requirement.value?.images,
                            position
                        )
                    )
                }
            )

            requestTypeGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    filterOption1.id -> {
                        amount.visibility = View.VISIBLE
                        requestDetail.visibility = View.GONE
                        viewModel.transmissionType = "통합견적"
                    }

                    filterOption2.id -> {
                        amount.visibility = View.GONE
                        requestDetail.visibility = View.VISIBLE
                        viewModel.transmissionType = "항목별견적"
                        totalAmount.setEditTextBackground(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.shape_fill_gray_background_gray_border,
                                null
                            )
                        )
                    }
                }
            }

            amount.addTextChangedListener(afterTextChanged = {
                amount.alertVisible =
                    amount.text.isNullOrEmpty() || amount.text.toString().replace(",", "")
                        .toLong() < 10000
            })

            amount.addFocusChangeListener(onFocusChange = { _, hasFocus ->
                val amountData = amount.text.toString().replace(",", "")
                if (hasFocus) amount.text =
                    amountData else if (!amountData.isNullOrEmpty()) amount.text =
                    "${DecimalFormat("#,###").format(amountData.toLong())}"
            })

            laborCost.addTextChangedListener(afterTextChanged = {
                laborCost.alertVisible = laborCost.text.isNullOrEmpty()
                setTotalAmount()
            })

            materialCost.addTextChangedListener(afterTextChanged = {
                materialCost.alertVisible = materialCost.text.isNullOrEmpty()
                setTotalAmount()
            })

            travelCost.addTextChangedListener(afterTextChanged = {
                travelCost.alertVisible = travelCost.text.isNullOrEmpty()
                setTotalAmount()
            })

        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                SEND_MESSAGE_SUCCESSFULLY -> {
                    toast(getString(R.string.send_message_succeeded))
                    super.onBackPressed()
                }
                SEND_MESSAGE_FAILED -> {
                    toast(getString(R.string.send_message_failed))
                }
            }
        })
        viewModel.requirement.observe(this, { requirement ->
            // TODO: 2021/06/16 고객 요청내용 추가되면 바꿔줘야함
//            val additionInfo = requirement?.additionalInfo
//            if (!additionInfo.isNullOrEmpty()) {
//                binding.customFrame.removeAllViews()
//                additionInfo.forEach { item ->
//                    addAdditionInfoView(
//                        binding.customFrame,
//                        this,
//                        item.description,
//                        item.value
//                    )
//                }
//            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestRequirement()
    }

    private fun setTotalAmount() {
        bind {
            val laborCostInt =
                if (!laborCost.text.isNullOrEmpty()) laborCost.text.toString().toLong() else 0
            val materialCostInt =
                if (!materialCost.text.isNullOrEmpty()) materialCost.text.toString().toLong() else 0
            val travelCostInt =
                if (!travelCost.text.isNullOrEmpty()) travelCost.text.toString().toLong() else 0

            totalAmount.text =
                "${DecimalFormat("#,###").format(laborCostInt + materialCostInt + travelCostInt)}"
        }
    }

    companion object {
        private const val TAG = "WriteEstimateActivity"
    }
}