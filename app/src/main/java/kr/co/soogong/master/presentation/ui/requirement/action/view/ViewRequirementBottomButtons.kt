package kr.co.soogong.master.presentation.ui.requirement.action.view

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityViewRequirementBinding
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.domain.entity.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.uihelper.requirment.action.CancelActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.utility.extension.dp
import timber.log.Timber

// 버튼은 총 4가지로 구성되어 있다.
// #1 버튼 : RequirementBasic 에 포함된 전화연결 버튼
// #2 버튼 : flexibleContainer 하단에 추가되는 버튼
// #3, 4 버튼 : ViewRequirement 하단에 고정되는 좌/우 버튼
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
            // Buttons : 2. 시공완료, 3. 견적거절, 4. 견적보내기
            is RequirementStatus.Requested -> {
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
                buttonLeft.setRefusingEstimation(activity.supportFragmentManager, viewModel)
//                buttonRight.setAcceptingEstimation(requirementId = viewModel.requirement.value?.id!!)
            }

            // 매칭대기
            // Buttons : 1. 전화연결, 3. 시공완료
            is RequirementStatus.Estimated -> {
                binding.requirementBasic.buttonCallToCustomerVisibility = true
                buttonRight.setEndingRepair(viewModel)
            }

            // 상담요청
            // Buttons: 1. 전화연결,
            RequirementStatus.RequestConsult -> {
                binding.requirementBasic.buttonCallToCustomerVisibility = true

                // 견적을 냈으면 -> 매칭대기와 같이 3. 시공완료
                // 견적을 안 냈으면 -> 견적요청과 같이 2. 시공완료, 3. 견적거절, 4. 견적보내기
                if (requirement.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED) {
                    buttonRight.setEndingRepair(viewModel)
                } else {
                    addEndRepairButton(activity, binding.flexibleContainer, requirement)
                    buttonLeft.setRefusingEstimation(activity.supportFragmentManager, viewModel)
//                    buttonRight.setAcceptingEstimation(requirementId = viewModel.requirement.value?.id!!)
                }
            }

            // 실측요청
            // Buttons : 1. 전화연결, 2. 시공완료, 3. 실측거절, 4. 실측수락
            is RequirementStatus.RequestMeasure -> {
                binding.requirementBasic.buttonCallToCustomerVisibility = true
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
                buttonLeft.setRefusingMeasure(activity.supportFragmentManager, viewModel)
//                buttonRight.setAcceptingMeasure(activity.supportFragmentManager, viewModel,
//                    EstimationDto(
//                        id = requirement.estimationDto?.id,
//                        requirementId = requirement.id,
//                        masterResponseCode = EstimationResponseCode.ACCEPTED,
//                        masterId = null,
//                        token = null,
//                        typeCode = null,
//                        price = null
//                    )
//                )
            }

            // 실측예정
            // Buttons : 1. 전화연결, 2. 시공완료, 3. 실측취소, 4. 실측입력
            is RequirementStatus.Measuring -> {
                binding.requirementBasic.buttonCallToCustomerVisibility = true
                addEndRepairButton(activity, binding.flexibleContainer, requirement)
                buttonLeft.setCancelingMeasure(viewModel)
                buttonRight.setSendingMeasure(viewModel)
            }

            // 실측완료, 시공예정
            // Buttons : 1. 전화연결, 2. 시공취소, 3. 시공완료
            is RequirementStatus.Measured, RequirementStatus.Repairing -> {
                binding.requirementBasic.buttonCallToCustomerVisibility = true
                buttonLeft.setCancelingRepair(viewModel)
                buttonRight.setEndingRepair(viewModel)
            }

            // 시공완료
            // Button : 1. 리뷰요청
            is RequirementStatus.Done -> {
                viewModel.requirement.value?.estimationDto?.repair?.requestReviewYn?.run {
                    if (this) buttonLeft.setAskingReviewDone()
                    else buttonRight.setAskingReview(viewModel)
                }
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
        // NOTE : style 로 정의된 theme 을 적용하려고, ContextThemeWrapper 과 병행하여 AppCompatButton 생성 시 style 을 추가! 무슨 이유인지 하나만 하면 적용이 안 됨.
        // TODO: 2022/02/09 차후 이거 적용해보자 : https://medium.com/mobile-app-development-publication/dynamically-change-android-views-style-56b18e59b33b
        AppCompatButton(
            ContextThemeWrapper(context, R.style.button_medium_outlined_secondary),
            null,
            R.style.button_medium_outlined_secondary)
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
    text = context.getString(R.string.request_review)
//    setOnClickListener { viewModel.askForReview() }
}

private fun AppCompatButton.setAskingReviewDone() {
    isVisible = true
    text = context.getString(R.string.request_review_done)
    isClickable = false
}

