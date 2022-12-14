package kr.co.soogong.master.presentation.ui.requirement.action.view

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.presentation.uihelper.requirment.action.CancelActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.MeasureActivityHelper
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
            // 방문요청
            // Buttons : 1. 거절하기, 2. 전화하기
            is RequirementStatus.RequestMeasure -> {
                buttonLeft.setRefusingMeasure(viewModel)
                buttonRight.setCallToClient(viewModel)
            }

            // 방문예정
            // Buttons : 1. 시공거절, 2. 방문일 입력, 3. 시공완료
            is RequirementStatus.Measuring -> {
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
                buttonLeft.setRefusingMeasure(viewModel)
                buttonRight.setSetVisitingDate(viewModel)
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

private fun AppCompatButton.setCallToClient(
    viewModel: ViewRequirementViewModel,
) {
    isVisible = true
    text = context.getString(R.string.accept_estimation)
    setOnClickListener {
        viewModel.showAcceptingEstimation()
    }
}

private fun AppCompatButton.setRefusingMeasure(
    viewModel: ViewRequirementViewModel,
) {
    isVisible = true
    text = context.getString(R.string.refuse_estimation)
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

private fun AppCompatButton.setSetVisitingDate(
    viewModel: ViewRequirementViewModel,
) {
    isVisible = true
    text = context.getString(R.string.insert_visiting_date)
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

    viewModel.requirement.value?.estimation?.repair?.requestReviewYn?.let { isRequestedReview ->
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

