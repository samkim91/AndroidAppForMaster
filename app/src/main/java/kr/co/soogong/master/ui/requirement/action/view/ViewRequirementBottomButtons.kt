package kr.co.soogong.master.ui.requirement.action.view

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
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
import kr.co.soogong.master.utility.extension.dp
import timber.log.Timber

// 버튼은 총 3가지로 구성되어 있다.
// #1 버튼 : flexibleContainer 하단에 추가되는 버튼
// #2, 3 버튼 : ViewRequirement 하단에 고정되는 좌/우 버튼
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
                // buttons : 1. 시공 완료, 2. 견적 거절, 3. 견적 보내기
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
                setRefuseEstimationButton(activity,
                    activity.supportFragmentManager,
                    viewModel,
                    buttonLeft)
                setAcceptEstimationButton(activity, viewModel, buttonRight)
            }

            is RequirementStatus.Estimated -> {
                // Button : 3. 시공 완료
                setEndRepairButton(activity, viewModel, buttonRight)
            }

            is RequirementStatus.RequestMeasure -> {
                // Buttons : 1. 시공 완료, 2. 실측 거절, 3. 실측 수락
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
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
                // Buttons : 1. 시공 완료, 2. 실측 취소, 3. 실측 입력
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
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

private fun addEndRepairButton(
    context: Context,
    container: ViewGroup,
    requirementDto: RequirementDto,
) {
    val params = LinearLayoutCompat.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 24.dp, 0, 28.dp)
    }

    val textView = AppCompatTextView(context).apply {
        height = 48.dp
        text = context.getString(R.string.repair_done_text)
        gravity = Gravity.CENTER
        background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_solid_white_stroke_light_grey2_radius8,
            null)
        setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        setTextAppearance(R.style.foot_note_regular)
        setOnClickListener {
            context.startActivity(
                EndRepairActivityHelper.getIntent(
                    context,
                    requirementDto.id
                )
            )
        }
    }

    container.addView(textView, params)
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

