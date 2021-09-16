package kr.co.soogong.master.ui.requirement.action.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.*
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getAcceptMeasureDialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getRefuseEstimateDialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getRefuseMeasureDialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getRequestConsultAlertDialogData
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.ASK_FOR_REVIEW_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.CALL_TO_CUSTOMER_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.INVALID_REQUIREMENT
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.REFUSE_TO_ESTIMATE_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.RESPOND_TO_MEASURE_SUCCESSFULLY
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.CANCEL_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.ESTIMATION_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.REPAIR_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.REQUIREMENT_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.REVIEW_TYPE
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.*
import kr.co.soogong.master.utility.EventObserver
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
                button.text = getString(R.string.progress_ending_text)
                button.setOnClickListener {
                    viewModel.requirement.value?.id?.let {
                        startActivity(
                            EndRepairActivityHelper.getIntent(this@ViewRequirementActivity, it)
                        )
                    }
                }
                root.findViewById<AppCompatButton>(R.id.button).isVisible = false
            }

            callToCustomerButton.setOnClickListener {
                viewModel.callToClient()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.requirement.observe(this@ViewRequirementActivity, { requirement ->
            setLayout(requirement)
            setButtons(requirement)
            binding.requirementStatus.requirementDto = requirement
            setSpecificLayout(requirement)
        })

        viewModel.action.observe(this@ViewRequirementActivity, EventObserver { event ->
            when (event) {
                REFUSE_TO_ESTIMATE_SUCCESSFULLY -> {
                    toast(getString(R.string.refuse_to_estimate_or_measure_successfully_text))
                    onBackPressed()
                }
                INVALID_REQUIREMENT -> {
                    onBackPressed()
                }
                CALL_TO_CUSTOMER_SUCCESSFULLY -> {
                    viewModel.requirement.value?.let {
                        startActivity(CallToCustomerHelper.getIntent(it.tel))
                    }
                }
                RESPOND_TO_MEASURE_SUCCESSFULLY -> {
                    toast(getString(R.string.respond_to_measure_successfully_text))
                    onBackPressed()
                }
                ASK_FOR_REVIEW_SUCCESSFULLY -> {
                    setReviewAsked()
                    toast(getString(R.string.ask_for_review_successful))
                }
                REQUEST_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun setSpecificLayout(requirement: RequirementDto) {
        (RequirementStatus.getStatusFromRequirement(requirement) == RequestConsult).let { boolean ->
            binding.callToCustomerButton.isVisible = boolean

            if (boolean) {
                val dialog = CustomDialog.newInstance(
                    dialogData = getRequestConsultAlertDialogData(this),
                    yesClick = { },
                    noClick = { }
                )
                dialog.show(supportFragmentManager, dialog.tag)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestRequirement()
    }

    private fun setLayout(requirement: RequirementDto) {
        Timber.tag(TAG).d("setLayout: ")
        bind {
            flexibleContainer.removeAllViews()
            actionBar.title.text = requirement.address

            when (RequirementStatus.getStatusFromRequirement(requirement)) {
                Requested, RequestConsult, RequestMeasure -> {
                    actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                    // view : 고객 요청 내용(spread)
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                }

                Estimated -> {
                    actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                    // view : 나의 제안 내용(spread), 고객 요청 내용
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = ESTIMATION_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }

                Repairing -> {
                    // view : 고객 요청 내용(spread, includingCancel), 나의 제안 내용
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = true,
                        includingCancel = true
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }

                RequestFinish -> {
                    // view : 고객 요청 내용(spread), 나의 제안 내용
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }

                Measuring -> {
                    // view : 고객요청(spread, includingCancel)
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = true,
                        includingCancel = true
                    )
                }

                Measured -> {
                    // view : 나의 실측 내용(spread, includingCancel), 고객요청
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = ESTIMATION_TYPE,
                        isSpread = true,
                        includingCancel = true
                    )
                }

                Done -> {
                    // view : 나의 최종 시공 내용(spread), 나의 제안 내용, 고객 요청 내용
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REPAIR_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }

                Closed -> {
                    // view : 고객 리뷰(spread), 나의 최종 시공 내용, 고객 요청 내용
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REVIEW_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = ESTIMATION_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                }

                Canceled -> {
                    // view : 시공 취소 사유, 고객 요청 내용
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = CANCEL_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                    requirementStatus.isVisible = false
                }
                else -> Unit
            }
        }
    }

    private fun setButtons(requirementDto: RequirementDto) {
        Timber.tag(TAG).d("setButtons: ")
        with(binding) {
            when (RequirementStatus.getStatusFromRequirement(requirementDto)) {
                Requested, RequestConsult -> {
                    // Buttons : 견적을 내기 어려워요 / 견적을 보낼래요.
                    leftButton.text = getString(R.string.refuse_estimate_text)
                    leftButton.setTextColor(getColor(R.color.color_FFFFFF))
                    leftButton.setBackgroundColor(resources.getColor(R.color.color_FF711D, null))
                    leftButton.setOnClickListener {
                        val dialog = CustomDialog.newInstance(
                            getRefuseEstimateDialogData(this@ViewRequirementActivity),
                            yesClick = {
                                viewModel.refuseToEstimate()
                            },
                            noClick = { }
                        )
                        dialog.show(supportFragmentManager, dialog.tag)
                    }
                    rightButton.text = getString(R.string.accept_estimate_text)
                    rightButton.setTextColor(getColor(R.color.color_FFFFFF))
                    rightButton.setBackgroundColor(resources.getColor(R.color.color_22D47B, null))
                    rightButton.setOnClickListener {
                        startActivity(
                            WriteEstimationActivityHelper.getIntent(
                                this@ViewRequirementActivity,
                                requirementId
                            )
                        )
                    }
                }

                RequestMeasure -> {
                    // Buttons : 실측 안할래요 / 실측 할래요
                    leftButton.text = getString(R.string.refuse_measure_text)
                    leftButton.setTextColor(getColor(R.color.color_FFFFFF))
                    leftButton.setBackgroundColor(resources.getColor(R.color.color_FF711D, null))
                    leftButton.setOnClickListener {
                        val dialog = CustomDialog.newInstance(
                            getRefuseMeasureDialogData(this@ViewRequirementActivity),
                            yesClick = {
                                startActivity(
                                    CancelActivityHelper.getIntent(
                                        this@ViewRequirementActivity,
                                        requirementId
                                    )
                                )
                            },
                            noClick = { }
                        )
                        dialog.show(supportFragmentManager, dialog.tag)
                    }
                    rightButton.text = getString(R.string.accept_measure_text)
                    rightButton.setTextColor(getColor(R.color.color_FFFFFF))
                    rightButton.setBackgroundColor(resources.getColor(R.color.color_22D47B, null))
                    rightButton.setOnClickListener {
                        val dialog = CustomDialog.newInstance(
                            getAcceptMeasureDialogData(this@ViewRequirementActivity),
                            yesClick = {
                                viewModel.respondToMeasure()
                            },
                            noClick = { }
                        )
                        dialog.show(supportFragmentManager, dialog.tag)
                    }
                }

                Repairing, RequestFinish -> {
                    // Buttons : 고객에게 전화하기 / 시공 완료
                    leftButton.text =
                        getString(R.string.call_to_customer_text)     // TODO: 2021/08/23 다시 전화하기 버튼 활성화..
                    leftButton.setTextColor(getColor(R.color.color_1FC472))
                    leftButton.setBackgroundColor(resources.getColor(R.color.color_FFFFFF, null))
                    leftButton.setOnClickListener {
                        viewModel.callToClient()
                    }
                    rightButton.text = getString(R.string.repair_done_text)
                    rightButton.setTextColor(getColor(R.color.color_FFFFFF))
                    rightButton.setBackgroundColor(resources.getColor(R.color.color_22D47B, null))
                    rightButton.setOnClickListener {
                        startActivity(
                            EndRepairActivityHelper.getIntent(
                                this@ViewRequirementActivity,
                                requirementId
                            )
                        )
                    }
                }

                Measuring -> {
                    // Buttons : 고객에게 전화하기 / 견적서 보내기
                    leftButton.text =
                        getString(R.string.call_to_customer_text)     // TODO: 2021/08/23 다시 전화하기 버튼 활성화..
                    leftButton.setTextColor(getColor(R.color.color_1FC472))
                    leftButton.setBackgroundColor(resources.getColor(R.color.color_FFFFFF, null))
                    leftButton.setOnClickListener {
                        viewModel.callToClient()
                    }
                    rightButton.text = getString(R.string.send_estimation)
                    rightButton.setTextColor(getColor(R.color.color_FFFFFF))
                    rightButton.setBackgroundColor(resources.getColor(R.color.color_22D47B, null))
                    rightButton.setOnClickListener {
                        startActivity(
                            MeasureActivityHelper.getIntent(
                                this@ViewRequirementActivity,
                                requirementId
                            )
                        )
                    }
                }

                Measured -> {
                    // Button : 고객에게 전화하기
                    leftButton.text =
                        getString(R.string.call_to_customer_text)     // TODO: 2021/08/23 다시 전화하기 버튼 활성화..
                    leftButton.setTextColor(getColor(R.color.color_1FC472))
                    leftButton.setBackgroundColor(resources.getColor(R.color.color_FFFFFF, null))
                    leftButton.setOnClickListener {
                        viewModel.callToClient()
                    }
                    rightButton.isVisible = false
                }

                Done -> {
                    // Button : 리뷰 요청하기
                    rightButton.isVisible = false
                    requirementDto.estimationDto?.repair?.requestReviewYn?.let { requestReviewYn ->
                        if (requestReviewYn) {
                            leftButton.text = getString(R.string.ask_for_review_successful)
                            leftButton.setTextColor(getColor(R.color.color_FFFFFF))
                            leftButton.setBackgroundColor(
                                resources.getColor(
                                    R.color.color_90E9BD,
                                    null
                                )
                            )
                        } else {
                            leftButton.text = getString(R.string.request_review_text)
                            leftButton.setTextColor(getColor(R.color.color_FFFFFF))
                            leftButton.setBackgroundColor(
                                resources.getColor(
                                    R.color.color_1FC472,
                                    null
                                )
                            )
                            leftButton.setOnClickListener {
                                viewModel.askForReview()
                            }
                        }
                    }
                }

                else -> {
                    buttonsDivider.isVisible = false
                    leftButton.isVisible = false
                    rightButton.isVisible = false
                }
            }
        }
    }

    private fun setReviewAsked() {
        binding.rightButton.isEnabled = false
        binding.rightButton.text = getString(R.string.ask_for_review_successful)
    }

    companion object {
        private const val TAG = "ViewEstimateActivity"
    }
}
