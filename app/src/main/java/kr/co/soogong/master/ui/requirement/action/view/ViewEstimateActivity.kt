package kr.co.soogong.master.ui.requirement.action.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.uihelper.image.ImageViewActivityHelper
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.cancel.CancelEstimateActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.end.EndEstimateActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.view.ViewEstimateActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.write.WriteEstimateActivityHelper
import kr.co.soogong.master.data.model.requirement.EstimationStatus
import kr.co.soogong.master.data.model.requirement.Message
import kr.co.soogong.master.databinding.ActivityViewEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getRefuseEstimateDialogData
import kr.co.soogong.master.ui.image.RectangleImageAdapter
import kr.co.soogong.master.ui.requirement.action.view.ViewEstimateViewModel.Companion.ASK_FOR_REVIEW_FAILED
import kr.co.soogong.master.ui.requirement.action.view.ViewEstimateViewModel.Companion.ASK_FOR_REVIEW_SUCCEEDED
import kr.co.soogong.master.ui.requirement.action.view.ViewEstimateViewModel.Companion.CALL_TO_CUSTOMER_FAILED
import kr.co.soogong.master.ui.requirement.action.view.ViewEstimateViewModel.Companion.CALL_TO_CUSTOMER_SUCCEEDED
import kr.co.soogong.master.ui.requirement.action.view.ViewEstimateViewModel.Companion.REFUSE_TO_ESTIMATE_FAILED
import kr.co.soogong.master.ui.requirement.action.view.ViewEstimateViewModel.Companion.REFUSE_TO_ESTIMATE_SUCCEEDED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.addAdditionInfoView
import kr.co.soogong.master.utility.extension.addTransmissionMessage
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class ViewEstimateActivity : BaseActivity<ActivityViewEstimateBinding>(
    R.layout.activity_view_estimate
) {
    private val estimationId: String by lazy {
        ViewEstimateActivityHelper.getEstimationId(intent)
    }

    private val viewModel: ViewEstimateViewModel by viewModels()

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
            lifecycleOwner = this@ViewEstimateActivity

            with(actionBar) {
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            photoList.adapter = RectangleImageAdapter(
                cardClickListener = { position ->
                    startActivity(
                        ImageViewActivityHelper.getIntent(
                            this@ViewEstimateActivity,
                            viewModel.estimation.value?.images,
                            position
                        )
                    )
                }
            )

            // 견적을 보낼래요 버튼
            acceptButton.setOnClickListener {
                startActivity(
                    WriteEstimateActivityHelper.getIntent(
                        this@ViewEstimateActivity,
                        estimationId
                    )
                )
            }

            // 견적을 내기 어려워요 버튼
            refuseButton.setOnClickListener {
                val dialog = CustomDialog(getRefuseEstimateDialogData(this@ViewEstimateActivity),
                    yesClick = {
                        viewModel.refuseToEstimate()
                    },
                    noClick = { }
                )

                dialog.show(supportFragmentManager, dialog.tag)
            }

            // 취소 됐음 버튼
            cancelButton.setOnClickListener {
                startActivity(
                    CancelEstimateActivityHelper.getIntent(
                        this@ViewEstimateActivity,
                        estimationId
                    )
                )
            }

            // 시공 완료 버튼
            doneButton.setOnClickListener {
                startActivity(
                    EndEstimateActivityHelper.getIntent(
                        this@ViewEstimateActivity,
                        estimationId
                    )
                )
            }

            // 리뷰 요청하기 버튼
            askReviewButton.setOnClickListener {
                viewModel.askForReview()
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.estimation.observe(this@ViewEstimateActivity, { estimation ->

            bind {
                actionBar.title.text =
                    getString(R.string.view_estimate_title, estimation?.keycode)

                val status =
                    EstimationStatus.getStatus(estimation?.status, estimation?.transmissions)


                // 고객 요청 내용
                val additionInfo = estimation?.additionalInfo
                if (!additionInfo.isNullOrEmpty()) {
                    customFrame.removeAllViews()
                    additionInfo.forEach { item ->
                        addAdditionInfoView(
                            customFrame,
                            this@ViewEstimateActivity,
                            item.description,
                            item.value
                        )
                    }
                }

                when (status) {
                    // 상태 : 견적요청
                    // view : 고객 요청 내용
                    // footer : 견적 마감일, 견적을 보낼래요, 견적을 내기 어려워요
                    EstimationStatus.Request -> {
                        requestedButtonGroup.visibility = View.VISIBLE
                    }

                    // 상태 : 매칭대기
                    // view : 고객 요청 내용, 나의 제안 내용
                    // footer : none
                    EstimationStatus.Waiting -> {
                        bindTransmissionData(estimation?.transmissions?.message)
                    }

                    // 상태 : 시공진행중
                    // view : 고객 요청 내용(+고객에게 전화하기), 나의 제안 내용
                    // footer : 취소 됐음, 시공 완료
                    EstimationStatus.Progress -> {
                        cancelButton.visibility = View.VISIBLE
                        doneButton.visibility = View.VISIBLE
                        callToCustomerContainer.visibility = View.VISIBLE
                        bindTransmissionData(estimation?.transmissions?.message)
                    }

                    // 상태 : 고객완료요청
                    // view : 고객 요청 내용, 나의 제안 내용
                    // footer : 시공 완료
                    EstimationStatus.CustomDone -> {
                        doneButton.visibility = View.VISIBLE
                        bindTransmissionData(estimation?.transmissions?.message)
                    }

                    // 상태 : 시공완료
                    // view : 나의 최종 시공 내용, 고객 요청 내용
                    // footer : 리뷰 요청하기
                    EstimationStatus.Done -> {
                        askReviewButton.visibility = View.VISIBLE
//                        Todo.. 리뷰상태 확인 및 변경
//                        if(askedReview){
//                            changeAskingReviewButton()
//                        }
                        bindDoneData(estimation?.transmissions?.message)
                    }

                    // 상태 : 평가완료
                    // view : 고객 리뷰, 나의 최종 시공 내용, 고객 요청 내용
                    // footer : none
                    EstimationStatus.Final -> {
                        //Todo.. 고객 리뷰 데이터를 서버에서 return 해줘야함.
                        customerReviewGroup.visibility = View.VISIBLE
                        bindDoneData(estimation?.transmissions?.message)
                    }

                    // 상태 : 시공취소
                    // view : 고객 시공 취소 사유, 고객 요청 내용, 나의 제안 내용
                    // footer : none
                    EstimationStatus.Cancel -> {
                        doneGroup.visibility = View.VISIBLE
                        bindTransmissionData(estimation?.transmissions?.message)
                    }
                }
            }
        })

        viewModel.action.observe(this@ViewEstimateActivity, EventObserver { event ->
            when (event) {
                REFUSE_TO_ESTIMATE_SUCCEEDED -> {
                    toast(getString(R.string.view_estimate_on_refuse_to_estimate_success))
                    onBackPressed()
                }
                REFUSE_TO_ESTIMATE_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
                CALL_TO_CUSTOMER_SUCCEEDED, CALL_TO_CUSTOMER_FAILED -> {
                    // Todo.. viewModel.estimation.number가 들어가야함
                    startActivity(CallToCustomerHelper.getIntent("viewModel.estimation.phoneNumber"))
                }
                ASK_FOR_REVIEW_SUCCEEDED -> {
                    changeAskingReviewButton()
                    toast(getString(R.string.ask_for_review_successful))
                }
                ASK_FOR_REVIEW_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun bindTransmissionData(message: Message?) {
        if (message != null) {
            binding.transmissionGroup.visibility = View.VISIBLE
            binding.customFrameForTransmissionDetail.removeAllViews()
            addTransmissionMessage(
                binding.customFrameForTransmissionDetail,
                this@ViewEstimateActivity,
                message
            )
        }
    }

    private fun bindDoneData(message: Message?) {
        if (message != null) {
            binding.doneGroup.visibility = View.VISIBLE
            binding.customFrameForDoneDetail.removeAllViews()
            addTransmissionMessage(
                binding.customFrameForDoneDetail,
                this@ViewEstimateActivity,
                message
            )
        }
    }

    private fun changeAskingReviewButton() {
        binding.askReviewButton.text = getString(R.string.ask_for_review_successful)
        binding.askReviewButton.background = getDrawable(R.color.color_90E9BD)
    }

    companion object {
        private const val TAG = "ViewEstimateActivity"
    }
}
