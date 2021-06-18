package kr.co.soogong.master.ui.requirement.action.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.Message
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getRefuseEstimateDialogData
import kr.co.soogong.master.ui.image.RectangleImageAdapter
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.ASK_FOR_REVIEW_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.CALL_TO_CUSTOMER_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.REFUSE_TO_ESTIMATE_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.image.ImageViewActivityHelper
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.CancelEstimationActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.WriteEstimationActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.addTransmissionMessage
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class ViewRequirementActivity : BaseActivity<ActivityViewRequirementBinding>(
    R.layout.activity_view_requirement
) {
    private val requirementId: Int by lazy {
        ViewRequirementActivityHelper.getRequirementId(intent)
    }

    private val viewModel: ViewRequirementViewModel by viewModels()

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
            lifecycleOwner = this@ViewRequirementActivity

            with(actionBar) {
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            photoList.adapter = RectangleImageAdapter(
                cardClickListener = { position ->
                    startActivity(
                        ImageViewActivityHelper.getIntent(
                            this@ViewRequirementActivity,
                            viewModel.requirement.value?.images,
                            position
                        )
                    )
                }
            )

            // 견적을 보낼래요 버튼
            acceptButton.setOnClickListener {
                startActivity(
                    WriteEstimationActivityHelper.getIntent(
                        this@ViewRequirementActivity,
                        requirementId
                    )
                )
            }

            // 견적을 내기 어려워요 버튼
            refuseButton.setOnClickListener {
                val dialog = CustomDialog(getRefuseEstimateDialogData(this@ViewRequirementActivity),
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
                    CancelEstimationActivityHelper.getIntent(
                        this@ViewRequirementActivity,
                        requirementId
                    )
                )
            }

            // 시공 완료 버튼
            doneButton.setOnClickListener {
                startActivity(
                    EndRepairActivityHelper.getIntent(
                        this@ViewRequirementActivity,
                        requirementId
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
        viewModel.requirement.observe(this@ViewRequirementActivity, { requirement ->

            bind {
                actionBar.title.text =
                    getString(R.string.view_requirement_action_bar_text, requirement?.token)

                // 고객 요청 내용
                // TODO: 2021/06/16 고객 요청내용 받아오고, view 에 추가하는 작업 해야함
//                val additionInfo = requirement?.additionalInfo
//                if (!additionInfo.isNullOrEmpty()) {
//                    customFrame.removeAllViews()
//                    additionInfo.forEach { item ->
//                        addAdditionInfoView(
//                            customFrame,
//                            this@ViewEstimateActivity,
//                            item.description,
//                            item.value
//                        )
//                    }
//                }

                when (RequirementStatus.getStatus(requirement.status)) {
                    // 상태 : 견적요청
                    // view : 고객 요청 내용
                    // footer button : 견적 마감일, 견적을 보낼래요, 견적을 내기 어려워요
                    RequirementStatus.Requested -> {
                        requestedButtonGroup.visibility = View.VISIBLE
                    }

                    // 상태 : 매칭대기
                    // view : 고객 요청 내용, 나의 제안 내용
                    // footer button : none
                    RequirementStatus.Estimated -> {
//                        bindEstimationData(requirement?.transmissions?.message)
                    }

                    // 상태 : 시공진행중
                    // view : 고객 요청 내용(+고객에게 전화하기), 나의 제안 내용
                    // footer button : 취소 됐음, 시공 완료
                    RequirementStatus.Repairing -> {
                        cancelButton.visibility = View.VISIBLE
                        doneButton.visibility = View.VISIBLE
                        callToCustomerContainer.visibility = View.VISIBLE
//                        bindEstimationData(requirement?.transmissions?.message)
                    }

                    // 상태 : 고객완료요청
                    // view : 고객 요청 내용, 나의 제안 내용
                    // footer button : 시공 완료
                    RequirementStatus.RequestFinish -> {
                        doneButton.visibility = View.VISIBLE
//                        bindEstimationData(requirement?.transmissions?.message)
                    }

                    // 상태 : 시공완료
                    // view : 나의 최종 시공 내용, 고객 요청 내용
                    // footer button : 리뷰 요청하기
                    RequirementStatus.Done -> {
                        askReviewButton.visibility = View.VISIBLE
//                        Todo.. 리뷰상태 확인 및 변경
//                        if(askedReview){
//                            changeAskingReviewButton()
//                        }
//                        bindDoneData(requirement?.transmissions?.message)
                    }

                    // 상태 : 평가완료
                    // view : 고객 리뷰, 나의 최종 시공 내용, 고객 요청 내용
                    // footer button : none
                    RequirementStatus.Closed -> {
                        //Todo.. 고객 리뷰 데이터를 서버에서 return 해줘야함.
                        customerReviewGroup.visibility = View.VISIBLE
//                        bindDoneData(requirement?.transmissions?.message)
                    }

                    // 상태 : 시공취소
                    // view : 취소 사유, 고객 요청 내용, 나의 제안 내용
                    // footer button : none
                    RequirementStatus.CanceledByClient, RequirementStatus.CanceledByMaster, RequirementStatus.Canceled -> {
                        doneGroup.visibility = View.VISIBLE
//                        bindEstimationData(requirement?.transmissions?.message)
                        // TODO: 2021/06/16 취소 사유 바인딩 필요
                    }

                    // 상태 : 매칭실패
                    // view : 취소 사유, 고객 요청 내용, 나의 제안 내용
                    // footer button : none
                    RequirementStatus.Failed -> {
//                        bindEstimationData(requirement?.estimationDto?.)
                    }

                    // 상태 : 기타
                    // view : 취소 사유, 고객 요청 내용, 나의 제안 내용
                    // footer button : none
                    else -> {
                        //                        bindEstimationData(requirement?.estimationDto?.)
                    }
                }
            }
        })



        viewModel.action.observe(this@ViewRequirementActivity, EventObserver { event ->
            when (event) {
                REFUSE_TO_ESTIMATE_SUCCESSFULLY -> {
                    toast(getString(R.string.view_estimate_on_refuse_to_estimate_success))
                    onBackPressed()
                }
                CALL_TO_CUSTOMER_SUCCESSFULLY -> {
                    viewModel.requirement.value?.let {
                        startActivity(CallToCustomerHelper.getIntent(it.tel))
                    }
                }
                ASK_FOR_REVIEW_SUCCESSFULLY -> {
                    changeAskingReviewButton()
                    toast(getString(R.string.ask_for_review_successful))
                }
                REQUEST_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestRequirement()
    }

    // TODO: 2021/06/16 추가 작업
    private fun bindEstimationData(message: Message?) {
        if (message != null) {
            binding.transmissionGroup.visibility = View.VISIBLE
            binding.customFrameForEstimationDetail.removeAllViews()
            addTransmissionMessage(
                binding.customFrameForEstimationDetail,
                this@ViewRequirementActivity,
                message
            )
        }
    }

    // TODO: 2021/06/16 추가 작업
    private fun bindDoneData(message: Message?) {
        if (message != null) {
            binding.doneGroup.visibility = View.VISIBLE
            binding.customFrameForDoneDetail.removeAllViews()
            addTransmissionMessage(
                binding.customFrameForDoneDetail,
                this@ViewRequirementActivity,
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
