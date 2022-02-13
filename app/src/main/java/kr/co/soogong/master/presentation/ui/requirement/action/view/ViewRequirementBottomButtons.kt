package kr.co.soogong.master.presentation.ui.requirement.action.view

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.uihelper.requirment.action.CancelActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.WriteEstimationActivityHelper
import kr.co.soogong.master.utility.extension.dp
import timber.log.Timber

// 버튼은 총 4가지로 구성되어 있다.
// #1, 2 버튼 : ViewRequirement 하단에 고정되는 좌/우 버튼
// #3 버튼 : flexibleContainer 하단에 추가되는 버튼
fun setBottomButtons(
    activity: ViewRequirementActivity,
    viewModel: ViewRequirementViewModel,
    binding: ActivityViewRequirementBinding,
    requirement: Requirement,
) {
    Timber.tag("ViewRequirementActivity").d("setBottomButtons: ")

    with(binding) {
        buttonLeft.isVisible = false
        buttonRight.isVisible = false

        when (requirement.status) {
            // 견적요청
            // Buttons : 1. 거절하기, 2. 견적 보내기, 3. 시공완료
            is RequirementStatus.Requested -> {
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
                buttonLeft.setRefusingEstimation(activity.supportFragmentManager, viewModel)
                buttonRight.setAcceptingEstimation(viewModel)
            }

            // 매칭대기
            // Buttons : 2. 시공완료
            is RequirementStatus.Estimated -> {
                buttonRight.setEndingRepair(viewModel)
            }

            // 상담요청
            // Buttons:
            // 견적을 냈으면 -> 매칭대기 2. 시공완료
            // 견적을 안 냈으면 -> 견적요청 1. 견적거절, 2. 견적보내기, 3. 시공완료
            RequirementStatus.RequestConsult -> {
                if (requirement.subStatus == RequirementStatus.Estimated.code) {
                    buttonRight.setEndingRepair(viewModel)
                } else {
                    addEndRepairButton(activity, binding.flexibleContainer, requirement)
                    buttonLeft.setRefusingEstimation(activity.supportFragmentManager, viewModel)
                    buttonRight.setAcceptingEstimation(viewModel)
                }
            }

            // 실측요청
            // Buttons : 1. 실측거절, 2. 실측수락
            is RequirementStatus.RequestMeasure -> {
                buttonLeft.setRefusingMeasure(activity.supportFragmentManager, viewModel)
                buttonRight.setAcceptingMeasure(activity.supportFragmentManager, viewModel)
            }

            // 실측예정
            // Buttons : 1. 실측취소, 2. 실측입력, 3. 시공완료
            is RequirementStatus.Measuring -> {
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
                buttonLeft.setCancelingMeasure(viewModel)
                buttonRight.setSendingMeasure(viewModel)
            }

            // 실측완료
            // Buttons : 1. 시공취소, 2. 시공완료
            is RequirementStatus.Measured -> {
                buttonLeft.setCancelingRepair(viewModel)
                buttonRight.setEndingRepair(viewModel)
            }

            // 시공예정
            // Buttons : 1. 시공취소, 2. 시공완료
            is RequirementStatus.Repairing -> {
                buttonLeft.setCancelingRepair(viewModel)
                buttonRight.setEndingRepair(viewModel)
            }

            // 시공완료
            // Button : 1. 리뷰요청
            is RequirementStatus.Done -> {
                buttonRight.setAskingReview(viewModel)
            }

            is RequirementStatus.Canceled, RequirementStatus.Closed ->
                buttonsContainer.isVisible = false
        }
    }
}

private fun addEndRepairButton(
    context: Context,
    container: ViewGroup,
    requirement: Requirement,
) {
    container.addView(
        // style 로 정의된 theme 을 적용하려고, ContextThemeWrapper 과 병행하여 AppCompatButton 생성 시 style 을 추가! 무슨 이유인지 하나만 하면 적용이 안 됨.
        AppCompatButton(
            ContextThemeWrapper(context, R.style.button_medium_tertiary_rounded), null, 0)
            .apply {
                text = context.getString(R.string.repair_done_text)
                height = 48.dp      // style 에 정의된 height 가 작용하지 않아서, 다시 set
                setOnClickListener {
                    context.startActivity(
                        EndRepairActivityHelper.getIntent(
                            context,
                            requirement.id
                        )
                    )
                }
            },
        // 위 아래 간격 부여
        LinearLayoutCompat.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 24.dp, 0, 24.dp)
        }
    )
}

private fun AppCompatButton.setAcceptingEstimation(
    viewModel: ViewRequirementViewModel,
) {
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

private fun AppCompatButton.setRefusingEstimation(
    fragmentManager: FragmentManager,
    viewModel: ViewRequirementViewModel,
) {
    isVisible = true
    text = context.getString(R.string.refuse_estimation)
    setOnClickListener {
        DefaultDialog.newInstance(
            DialogData.getRefuseEstimate()
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

private fun AppCompatButton.setEndingRepair(
    viewModel: ViewRequirementViewModel,
) {
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

private fun AppCompatButton.setRefusingMeasure(
    fragmentManager: FragmentManager,
    viewModel: ViewRequirementViewModel,
) {
    isVisible = true
    text = context.getString(R.string.refuse_measure)
    setOnClickListener {
        DefaultDialog.newInstance(
            DialogData.getRefuseMeasure()
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

private fun AppCompatButton.setAcceptingMeasure(
    fragmentManager: FragmentManager,
    viewModel: ViewRequirementViewModel,
) {
    isVisible = true
    text = context.getString(R.string.accept_measure)
    setOnClickListener {
        DefaultDialog.newInstance(
            DialogData.getAcceptMeasure()
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

private fun AppCompatButton.setCancelingMeasure(
    viewModel: ViewRequirementViewModel,
) {
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

private fun AppCompatButton.setCancelingRepair(
    viewModel: ViewRequirementViewModel,
) {
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

private fun AppCompatButton.setSendingMeasure(
    viewModel: ViewRequirementViewModel,
) {
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

private fun AppCompatButton.setAskingReview(
    viewModel: ViewRequirementViewModel,
) {
    isVisible = true
    setOnClickListener { viewModel.askForReview() }

    viewModel.requirement.value?.estimationDto?.repair?.requestReviewYn?.let { isRequestedReview ->
        isEnabled = !isRequestedReview

        text =
            context.getString(if (isRequestedReview) R.string.request_review_done else R.string.request_review)

        background = ResourcesCompat.getDrawable(resources,
            if (isRequestedReview) R.drawable.bg_solid_light_grey1_selector_radius30 else R.drawable.bg_solid_green_selector_radius30,
            null)

        setTextColor(ResourcesCompat.getColor(resources,
            if (isRequestedReview) R.color.grey_1 else R.color.white,
            null))
    }
}

