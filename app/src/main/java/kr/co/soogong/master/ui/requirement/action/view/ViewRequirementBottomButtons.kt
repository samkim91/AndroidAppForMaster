package kr.co.soogong.master.ui.requirement.action.view

import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.uihelper.requirment.action.CancelActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.WriteEstimationActivityHelper
import timber.log.Timber

// Note: 1차 리팩토링이고, 중복되는 함수들은 다시 빼내야함.
fun setBottomButtons(
    activity: ViewRequirementActivity,
    viewModel: ViewRequirementViewModel,
    binding: ActivityViewRequirementBinding,
    requirement: RequirementDto,
) {
    Timber.tag("ViewRequirementActivity").d("setBottomButtons: ")

    with(activity) {
        with(binding) {
            buttonsDivider.isVisible = false
            leftButton.isVisible = false
            rightButton.isVisible = false

            when (RequirementStatus.getStatusFromRequirement(requirement)) {
                is RequirementStatus.Requested, RequirementStatus.RequestConsult -> {
                    (requirement.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED).let { accepted ->
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
                                    DialogData.getRefuseEstimateDialogData(context)
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
                                        activity,
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
                                DialogData.getRefuseMeasureDialogData(activity)
                            ).let {
                                it.setButtonsClickListener(
                                    onPositive = {
                                        startActivity(
                                            CancelActivityHelper.getIntent(
                                                activity,
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
                                DialogData.getAcceptMeasureDialogData(activity)
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
                            requirement.estimationDto?.fromMasterCallCnt!! > 0 -> {
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
                                    activity,
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
                            requirement.estimationDto?.fromMasterCallCnt!! > 0 -> {
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
                                    activity,
                                    viewModel.requirementId.value!!
                                )
                            )
                        }
                    }
                }

                is RequirementStatus.Estimated -> {
                    if (requirement.safetyNumber.isNullOrEmpty()) return
                    // Button : 고객에게 전화하기
                    buttonsDivider.isVisible = true
                    with(leftButton) {
                        isVisible = true
                        when {
                            requirement.estimationDto?.fromMasterCallCnt!! > 0 -> {
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
                }

                is RequirementStatus.Measured -> {
                    // Button : 고객에게 전화하기
                    buttonsDivider.isVisible = true
                    with(leftButton) {
                        isVisible = true
                        when {
                            requirement.estimationDto?.fromMasterCallCnt!! > 0 -> {
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
                }

                is RequirementStatus.Done -> {
                    // Button : 리뷰 요청하기
                    buttonsDivider.isVisible = true
                    with(leftButton) {
                        isVisible = true
                        requirement.estimationDto?.repair?.requestReviewYn?.let { requestReviewYn ->
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
}