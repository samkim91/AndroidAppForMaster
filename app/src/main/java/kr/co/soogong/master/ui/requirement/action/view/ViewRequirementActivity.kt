package kr.co.soogong.master.ui.requirement.action.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.*
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getAcceptMeasureDialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getExpiredRequestConsultDialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getRefuseEstimateDialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getRefuseMeasureDialogData
import kr.co.soogong.master.ui.dialog.popup.DialogData.Companion.getRequestConsultAlertDialogData
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.ASK_FOR_REVIEW_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.CALL_TO_CUSTOMER_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.INVALID_REQUIREMENT
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.NOT_APPROVED_MASTER
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.REFUSE_TO_ESTIMATE_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.requirement.action.view.ViewRequirementViewModel.Companion.RESPOND_TO_MEASURE_SUCCESSFULLY
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.CANCEL_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.ESTIMATION_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.PREVIOUS_ESTIMATION_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.REPAIR_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.REQUIREMENT_TYPE
import kr.co.soogong.master.ui.widget.RequirementDrawerContainer.Companion.REVIEW_TYPE
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.*
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.formatDateWithoutDay
import kr.co.soogong.master.utility.extension.setDueDate
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class ViewRequirementActivity : BaseActivity<ActivityViewRequirementBinding>(
    R.layout.activity_view_requirement
) {
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
                    viewModel.requirement.value?.id?.let { id ->
                        CustomDialog.newInstance(
                            DialogData.getConfirmRepairDoneDialogData(this@ViewRequirementActivity)
                        ).let {
                            it.setButtonsClickListener(
                                onPositive = {
                                    startActivity(EndRepairActivityHelper.getIntent(this@ViewRequirementActivity,
                                        id))
                                },
                                onNegative = {}
                            )
                            it.show(supportFragmentManager, it.tag)
                        }
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
            if (!isValidRequirement(requirement)) return@observe
            setFlexibleContainerLayout(requirement)
            setButtons(requirement)
            binding.requirementDueDate.setDueDate(requirement)
            binding.requirementStatus.requirementDto = requirement
            setLayoutForRequestConsult(requirement)
            showDialogForCallingCustomer(requirement)
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
                        startActivity(CallToCustomerHelper.getIntent(if (it.safetyNumber.isNullOrEmpty()) it.tel else it.safetyNumber))
                    }
                }
                RESPOND_TO_MEASURE_SUCCESSFULLY -> {
                    // 화면 리프레시 하고 다이얼로그 띄우기
                    viewModel.requestRequirement()
                }
                ASK_FOR_REVIEW_SUCCESSFULLY -> {
                    setReviewAsked()
                    toast(getString(R.string.ask_for_review_successful))
                }
                NOT_APPROVED_MASTER -> {
                    toast(getString(R.string.not_approved_master))
                    onBackPressed()
                }
                REQUEST_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun setLayoutForRequestConsult(requirement: RequirementDto) {
        (RequirementStatus.getStatusFromRequirement(requirement) is RequirementStatus.RequestConsult).let { boolean ->
            binding.callToCustomerButton.isVisible = boolean

            // NOTE: 상호 통화한 적이 한번도 없으면 다이얼로그로 전화하라고 안내
            if (boolean && requirement.estimationDto?.fromMasterCallCnt == 0 && requirement.estimationDto.fromClientCallCnt == 0) {
                CustomDialog.newInstance(
                    getRequestConsultAlertDialogData(this)
                ).let {
                    it.setButtonsClickListener(
                        onPositive = {},
                        onNegative = {}
                    )
                    it.show(supportFragmentManager, it.tag)
                }
            }
        }
    }

    private fun showDialogForCallingCustomer(requirement: RequirementDto) {
        (RequirementStatus.getStatusFromRequirement(requirement) is RequirementStatus.Measuring).let { boolean ->
            if (boolean && requirement.estimationDto?.fromMasterCallCnt == 0 && requirement.estimationDto.fromClientCallCnt == 0) {
                CustomDialog.newInstance(DialogData.getRecommendingCallingCustomer(this))
                .let {
                    it.setButtonsClickListener(
                        onPositive = { viewModel.callToClient() },
                        onNegative = {}
                    )
                    it.show(supportFragmentManager, it.tag)
                }
            }
        }
    }

    private fun isValidRequirement(requirement: RequirementDto): Boolean {
        if (requirement.estimationDto?.masterResponseCode == EstimationResponseCode.EXPIRED) {
            CustomDialog.newInstance(
                dialogData = getExpiredRequestConsultDialogData(this),
                cancelable = false
            ).let {
                it.setButtonsClickListener(
                    onPositive = { onBackPressed() },
                    onNegative = {}
                )
                it.show(supportFragmentManager, it.tag)
            }
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestMasterSimpleInfo()
        viewModel.requestRequirement()
    }

    private fun setFlexibleContainerLayout(requirement: RequirementDto) {
        Timber.tag(TAG).d("setLayout: ")
        bind {
            flexibleContainer.removeAllViews()
            actionBar.title.text = requirement.address

            when (RequirementStatus.getStatusFromRequirement(requirement)) {
                is RequirementStatus.RequestMeasure -> {
                    actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = false
                    // view : 고객 요청 내용(spread), 이전 실측 내용(있으면, spread)
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = true,
                        includingCancel = false
                    )
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = true,
                            includingCancel = false
                        )
                    }
                }

                is RequirementStatus.Requested, RequirementStatus.RequestConsult -> {
                    actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                    (requirement.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED).let { accepted ->
                        if (accepted) {
                            // view : 나의 제안 내용(spread), 고객 요청 내용, 이전 실측 내용(있으면)
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
                            requirement.measurement?.let {
                                RequirementDrawerContainer.addDrawerContainer(
                                    context = this@ViewRequirementActivity,
                                    container = flexibleContainer,
                                    requirementDto = requirement,
                                    contentType = PREVIOUS_ESTIMATION_TYPE,
                                    isSpread = false,
                                    includingCancel = false
                                )
                            }
                            return@let
                        }
                        // view : 고객 요청 내용(spread), 이전 실측 내용(있으면, spread)
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = REQUIREMENT_TYPE,
                            isSpread = true,
                            includingCancel = false
                        )
                        requirement.measurement?.let {
                            RequirementDrawerContainer.addDrawerContainer(
                                context = this@ViewRequirementActivity,
                                container = flexibleContainer,
                                requirementDto = requirement,
                                contentType = PREVIOUS_ESTIMATION_TYPE,
                                isSpread = true,
                                includingCancel = false
                            )
                        }
                    }
                }

                is RequirementStatus.Estimated -> {
                    actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                    // view : 나의 제안 내용(spread), 고객 요청 내용, 이전 실측 내용(있으면)
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
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = false,
                            includingCancel = false
                        )
                    }
                }

                is RequirementStatus.Repairing -> {
                    // view : 고객 요청 내용(spread, includingCancel), 나의 제안 내용, 이전 실측 내용(있으면)
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
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = false,
                            includingCancel = false
                        )
                    }
                }

                is RequirementStatus.RequestFinish -> {
                    // view : 고객 요청 내용(spread), 나의 제안 내용, 이전 실측 내용(있으면)
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
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = false,
                            includingCancel = false
                        )
                    }
                }

                is RequirementStatus.Measuring -> {
                    actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                    // view : 고객요청(spread, includingCancel), 이전 실측 내용(있으면, spread)
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = true,
                        includingCancel = true
                    )
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = true,
                            includingCancel = false
                        )
                    }
                }

                is RequirementStatus.Measured -> {
                    actionBar.root.findViewById<AppCompatButton>(R.id.button).isVisible = true
                    // view : 나의 실측 내용(spread, includingCancel), 고객요청, 이전 실측 내용(있으면)
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = ESTIMATION_TYPE,
                        isSpread = true,
                        includingCancel = true
                    )
                    RequirementDrawerContainer.addDrawerContainer(
                        context = this@ViewRequirementActivity,
                        container = flexibleContainer,
                        requirementDto = requirement,
                        contentType = REQUIREMENT_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = false,
                            includingCancel = false
                        )
                    }
                }

                is RequirementStatus.Done -> {
                    // view : 나의 최종 시공 내용(spread), 고객 요청 내용, 이전 실측 내용(있으면)
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
                        contentType = REQUIREMENT_TYPE,
                        isSpread = false,
                        includingCancel = false
                    )
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = false,
                            includingCancel = false
                        )
                    }
                    // 시공완료일
                    requirementStatus.endingText = getString(
                        R.string.progress_ending_text_with_date,
                        requirement.estimationDto?.repair?.actualDate.formatDateWithoutDay()
                    )
                    requirementStatus.endingTextColor = getColor(R.color.color_0C5E47)
                }

                is RequirementStatus.Closed -> {
                    // view : 고객 리뷰(spread), 나의 최종 시공 내용, 고객 요청 내용, 이전 실측 내용(있으면)
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
                        contentType = REPAIR_TYPE,
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
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = false,
                            includingCancel = false
                        )
                    }
                    // 시공완료일
                    requirementStatus.endingText = getString(
                        R.string.progress_ending_text_with_date,
                        requirement.estimationDto?.repair?.actualDate.formatDateWithoutDay()
                    )
                    requirementStatus.endingTextColor = getColor(R.color.color_0C5E47)
                }

                is RequirementStatus.Canceled -> {
                    // view : 시공 취소 사유, 고객 요청 내용, 이전 실측 내용(있으면)
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
                    requirement.measurement?.let {
                        RequirementDrawerContainer.addDrawerContainer(
                            context = this@ViewRequirementActivity,
                            container = flexibleContainer,
                            requirementDto = requirement,
                            contentType = PREVIOUS_ESTIMATION_TYPE,
                            isSpread = false,
                            includingCancel = false
                        )
                    }
                    requirementStatus.isVisible = false
                }
            }
        }
    }

    private fun setButtons(requirementDto: RequirementDto) {
        Timber.tag(TAG).d("setButtons: ")
        with(binding) {
            buttonsDivider.isVisible = false
            leftButton.isVisible = false
            rightButton.isVisible = false

            when (RequirementStatus.getStatusFromRequirement(requirementDto)) {
                is RequirementStatus.Requested, RequirementStatus.RequestConsult -> {
                    (requirementDto.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED).let { accepted ->
                        if (accepted) return@let
                        // Buttons : 견적을 내기 어려워요 / 견적을 보낼래요.
                        buttonsDivider.isVisible = true
                        with(leftButton) {
                            isVisible = true
                            text = getString(R.string.refuse_estimate_text)
                            setTextColor(getColor(R.color.color_FFFFFF))
                            setBackgroundColor(resources.getColor(R.color.color_FF711D, null))
                            setOnClickListener {
                                CustomDialog.newInstance(
                                    getRefuseEstimateDialogData(this@ViewRequirementActivity)
                                ).let {
                                    it.setButtonsClickListener(
                                        onPositive = {
                                            viewModel.refuseToEstimate()
                                        },
                                        onNegative = { }
                                    )
                                    it.show(supportFragmentManager, it.tag)
                                }
                            }
                        }
                        with(rightButton) {
                            isVisible = true
                            text = getString(R.string.accept_estimate_text)
                            setTextColor(getColor(R.color.color_FFFFFF))
                            setBackgroundColor(resources.getColor(R.color.color_22D47B, null))
                            setOnClickListener {
                                startActivity(
                                    WriteEstimationActivityHelper.getIntent(
                                        this@ViewRequirementActivity,
                                        viewModel.requirementId.value!!
                                    )
                                )
                            }
                        }
                    }
                }

                is RequirementStatus.RequestMeasure -> {
                    // Buttons : 실측 안할래요 / 실측 할래요
                    buttonsDivider.isVisible = true
                    with(leftButton) {
                        isVisible = true
                        text = getString(R.string.refuse_measure_text)
                        setTextColor(getColor(R.color.color_FFFFFF))
                        setBackgroundColor(resources.getColor(R.color.color_FF711D, null))
                        setOnClickListener {
                            CustomDialog.newInstance(
                                getRefuseMeasureDialogData(this@ViewRequirementActivity)
                            ).let {
                                it.setButtonsClickListener(
                                    onPositive = {
                                        startActivity(
                                            CancelActivityHelper.getIntent(
                                                this@ViewRequirementActivity,
                                                viewModel.requirementId.value!!
                                            )
                                        )
                                    },
                                    onNegative = { }
                                )
                                it.show(supportFragmentManager, it.tag)
                            }
                        }
                    }
                    with(rightButton) {
                        isVisible = true
                        text = getString(R.string.accept_measure_text)
                        setTextColor(getColor(R.color.color_FFFFFF))
                        setBackgroundColor(resources.getColor(R.color.color_22D47B, null))
                        setOnClickListener {
                            CustomDialog.newInstance(
                                getAcceptMeasureDialogData(this@ViewRequirementActivity)
                            ).let {
                                it.setButtonsClickListener(
                                    onPositive = {
                                        viewModel.respondToMeasure()
                                    },
                                    onNegative = { }
                                )
                                it.show(supportFragmentManager, it.tag)
                            }
                        }
                    }
                }

                is RequirementStatus.Repairing, RequirementStatus.RequestFinish -> {
                    // Buttons : 고객에게 전화하기 / 시공 완료
                    buttonsDivider.isVisible = true
                    with(leftButton) {
                        isVisible = true
                        when {
                            requirementDto.estimationDto?.fromMasterCallCnt!! > 0 -> {
                                text = getString(R.string.recall_to_customer_text)
                                setTextColor(getColor(R.color.color_555555))
                            }
                            else -> {
                                text = getString(R.string.call_to_customer_text)
                                setTextColor(getColor(R.color.color_1FC472))
                            }
                        }
                        setBackgroundColor(resources.getColor(R.color.color_FFFFFF, null))
                        setOnClickListener {
                            viewModel.callToClient()
                        }
                    }
                    with(rightButton) {
                        isVisible = true
                        text = getString(R.string.repair_done_text)
                        setTextColor(getColor(R.color.color_FFFFFF))
                        setBackgroundColor(resources.getColor(R.color.color_22D47B, null))
                        setOnClickListener {
                            startActivity(
                                EndRepairActivityHelper.getIntent(
                                    this@ViewRequirementActivity,
                                    viewModel.requirementId.value!!
                                )
                            )
                        }
                    }
                }

                is RequirementStatus.Measuring -> {
                    // Buttons : 고객에게 전화하기 / 견적서 보내기
                    buttonsDivider.isVisible = true
                    with(leftButton) {
                        isVisible = true
                        when {
                            requirementDto.estimationDto?.fromMasterCallCnt!! > 0 -> {
                                text = getString(R.string.recall_to_customer_text)
                                setTextColor(getColor(R.color.color_555555))
                            }
                            else -> {
                                text = getString(R.string.call_to_customer_text)
                                setTextColor(getColor(R.color.color_1FC472))
                            }
                        }
                        setBackgroundColor(resources.getColor(R.color.color_FFFFFF, null))
                        setOnClickListener {
                            viewModel.callToClient()
                        }
                    }
                    with(rightButton) {
                        isVisible = true
                        text = getString(R.string.send_estimation)
                        setTextColor(getColor(R.color.color_FFFFFF))
                        setBackgroundColor(resources.getColor(R.color.color_22D47B, null))
                        setOnClickListener {
                            startActivity(
                                MeasureActivityHelper.getIntent(
                                    this@ViewRequirementActivity,
                                    viewModel.requirementId.value!!
                                )
                            )
                        }
                    }
                }

                is RequirementStatus.Measured -> {
                    // Button : 고객에게 전화하기
                    buttonsDivider.isVisible = true
                    with(leftButton) {
                        isVisible = true
                        when {
                            requirementDto.estimationDto?.fromMasterCallCnt!! > 0 -> {
                                text = getString(R.string.recall_to_customer_text)
                                setTextColor(getColor(R.color.color_555555))
                            }
                            else -> {
                                text = getString(R.string.call_to_customer_text)
                                setTextColor(getColor(R.color.color_1FC472))
                            }
                        }
                        setTextColor(getColor(R.color.color_1FC472))
                        setBackgroundColor(resources.getColor(R.color.color_FFFFFF, null))
                        setOnClickListener {
                            viewModel.callToClient()
                        }
                    }
                }

                is RequirementStatus.Done -> {
                    // Button : 리뷰 요청하기
                    buttonsDivider.isVisible = true
                    with(leftButton) {
                        isVisible = true
                        requirementDto.estimationDto?.repair?.requestReviewYn?.let { requestReviewYn ->
                            if (requestReviewYn) {
                                text = getString(R.string.ask_for_review_successful)
                                setTextColor(getColor(R.color.color_FFFFFF))
                                setBackgroundColor(
                                    resources.getColor(
                                        R.color.color_90E9BD,
                                        null
                                    )
                                )
                            } else {
                                text = getString(R.string.request_review_text)
                                setTextColor(getColor(R.color.color_FFFFFF))
                                setBackgroundColor(
                                    resources.getColor(
                                        R.color.color_1FC472,
                                        null
                                    )
                                )
                                setOnClickListener {
                                    viewModel.askForReview()
                                }
                            }
                        }
                    }
                }

                else -> {
                }
            }
        }
    }

    private fun setReviewAsked() {
        binding.rightButton.isEnabled = false
        binding.rightButton.text = getString(R.string.ask_for_review_successful)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Note: ViewRequirement 화면에서 notification 으로 해당 requirement 에 접근했을 때, 화면을 refresh 해주기 위함
        intent?.let {
            viewModel.requirementId.value = ViewRequirementActivityHelper.getRequirementId(it)
        }
        viewModel.requestRequirement()
    }

    companion object {
        private const val TAG = "ViewEstimateActivity"
    }
}
