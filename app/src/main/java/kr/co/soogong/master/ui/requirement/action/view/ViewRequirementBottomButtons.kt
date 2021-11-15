package kr.co.soogong.master.ui.requirement.action.view

import android.content.Context
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementStatus
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

    with(binding) {
        buttonLeft.isVisible = false
        buttonRight.isVisible = false

        when (RequirementStatus.getStatusFromRequirement(requirement)) {
            is RequirementStatus.Requested -> {
                // buttons : 견적 거절, 견적 보내기
                setRefuseEstimationButton(activity,
                    activity.supportFragmentManager,
                    viewModel,
                    buttonLeft)
                setAcceptEstimationButton(activity, viewModel, buttonRight)
            }

            is RequirementStatus.Estimated -> {
                // Button : 시공 완료
                setEndRepairButton(activity, viewModel, buttonRight)
            }

            is RequirementStatus.RequestMeasure -> {
                // Buttons : 실측 거절, 실측 수락
                setRefuseMeasureButton(activity,
                    activity.supportFragmentManager,
                    viewModel,
                    buttonLeft)
                setAcceptMeasureButton(activity,
                    activity.supportFragmentManager,
                    viewModel,
                    buttonRight)
            }

            is RequirementStatus.Measuring -> {
                // Buttons : 실측 취소, 실측 입력
                setCancelMeasureButton(activity, viewModel, buttonLeft)
                setSendMeasureButton(activity, viewModel, buttonRight)
            }

            is RequirementStatus.Measured, RequirementStatus.Repairing -> {
                // Button : 시공 취소, 시공 완료
                setCancelRepairButton(activity, viewModel, buttonLeft)
                setEndRepairButton(activity, viewModel, buttonRight)
            }

            is RequirementStatus.Done -> {
                // Button : 리뷰 요청
                setAskReviewButton(activity, viewModel, buttonRight)
            }

            else -> {
                buttonsContainer.isVisible = false
            }
        }
    }
}

private fun setAcceptEstimationButton(
    context: Context,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        text = context.getString(R.string.write_estimation)
        setOnClickListener {
            context.startActivity(
                WriteEstimationActivityHelper.getIntent(
                    context,
                    viewModel.requirementId.value!!
                )
            )
        }
    }
}

private fun setRefuseEstimationButton(
    context: Context,
    fragmentManager: FragmentManager,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        text = context.getString(R.string.refuse_estimation)
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
                it.show(fragmentManager, it.tag)
            }
        }
    }
}

private fun setEndRepairButton(
    context: Context,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        text = context.getString(R.string.repair_done_text)
        setOnClickListener {
            context.startActivity(
                EndRepairActivityHelper.getIntent(
                    context,
                    viewModel.requirementId.value!!
                )
            )
        }
    }
}

private fun setRefuseMeasureButton(
    context: Context,
    fragmentManager: FragmentManager,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        text = context.getString(R.string.refuse_measure)
        setOnClickListener {
            CustomDialog.newInstance(
                DialogData.getRefuseMeasureDialogData(context)
            ).let {
                it.setButtonsClickListener(
                    onPositive = {
                        context.startActivity(
                            CancelActivityHelper.getIntent(
                                context,
                                viewModel.requirementId.value!!
                            )
                        )
                    },
                    onNegative = { }
                )
                it.show(fragmentManager, it.tag)
            }
        }
    }
}

private fun setAcceptMeasureButton(
    context: Context,
    fragmentManager: FragmentManager,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        text = context.getString(R.string.accept_measure)
        setOnClickListener {
            CustomDialog.newInstance(
                DialogData.getAcceptMeasureDialogData(context)
            ).let {
                it.setButtonsClickListener(
                    onPositive = {
                        viewModel.respondToMeasure()
                    },
                    onNegative = { }
                )
                it.show(fragmentManager, it.tag)
            }
        }
    }
}

private fun setCancelMeasureButton(
    context: Context,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        text = context.getString(R.string.cancel_measure)
        setOnClickListener {
            context.startActivity(
                CancelActivityHelper.getIntent(
                    context,
                    viewModel.requirementId.value!!
                )
            )
        }
    }
}

private fun setCancelRepairButton(
    context: Context,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        text = context.getString(R.string.cancel_repair)
        setOnClickListener {
            context.startActivity(
                CancelActivityHelper.getIntent(
                    context,
                    viewModel.requirementId.value!!
                )
            )
        }
    }
}

private fun setSendMeasureButton(
    context: Context,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        text = context.getString(R.string.send_measure)
        setOnClickListener {
            context.startActivity(
                MeasureActivityHelper.getIntent(
                    context,
                    viewModel.requirementId.value!!
                )
            )
        }
    }
}

private fun setAskReviewButton(
    context: Context,
    viewModel: ViewRequirementViewModel,
    button: AppCompatButton,
) {
    with(button) {
        isVisible = true
        viewModel.requirement.value?.estimationDto?.repair?.requestReviewYn?.let { requestReviewYn ->
            if (requestReviewYn) {
                text = context.getString(R.string.request_review_done)
                isClickable = false
            } else {
                text = context.getString(R.string.request_review)
                setOnClickListener { viewModel.askForReview() }
            }
        }
    }
}

