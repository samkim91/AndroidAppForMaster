package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.CompareCodeTable
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.uihelper.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.extension.dp

// TODO: 2021/11/11 각 상태별로 나타내야하는 버튼이 정의되면 다시 작업 필요 !!!

// Requirement Card viewHolder 들의 부모클래스
open class RequirementCardViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(
        requirementCard: RequirementCard,
    ) {
        this.itemView.layoutParams =
            (this.itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = 4.dp
                bottomMargin = 4.dp
                marginStart = 16.dp
                marginEnd = 16.dp
            }

        with(binding) {
            buttonLeft.isVisible = false
            textViewDueTime.isVisible = false

            data = requirementCard

            setQnaChipGroup(requirementCard)

            buttonRight.isVisible = true
            buttonRight.setText(R.string.requirements_card_view_detail)
            setRightButtonClickListener {
                context.startActivity(
                    ViewRequirementActivityHelper.getIntent(
                        context,
                        requirementCard.id
                    )
                )
            }
        }
    }

    fun setRequirementCardStatusTheme(theme: Int) {
        with(binding.textViewStatus) {
            if (theme == THEME_BLUE) {
                backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.c_1A227ED4))
                setTextColor(context.getColor(R.color.brand_blue))
            } else {
                backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.background))
                setTextColor(context.getColor(R.color.dark_grey_1))
            }
        }
    }

    private fun setQnaChipGroup(requirementCard: RequirementCard) {
        with(binding.chipGroupQna) {
            this.removeAllViews()       // 데이터 바인딩 과정 중에 생긴 중복칩 삭제
            requirementCard.requirementQnas?.let { qnaList ->
                qnaList.take(6).map { qna ->
                    (LayoutInflater.from(context)
                        .inflate(R.layout.item_chip_choice_grey_rectangular, this, false) as Chip)
                        .also { chip ->
                            chip.text = qna.answer
                            chip.isClickable = false
                            this.addView(chip)
                        }
                }
            }
        }
    }

    fun setApprovedMasterOnly(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            setRightButtonClickListener {
                viewModel.masterSimpleInfo.value?.approvedStatus.let {
                    when (it) {
                        // 미승인 상태이면, 필수정보를 채우도록 이동
                        NotApprovedCodeTable.code -> {
                            CustomDialog.newInstance(
                                DialogData.getAskingFillProfileDialogData(context),
                            ).let { dialog ->
                                dialog.setButtonsClickListener(
                                    onPositive = {
                                        context.startActivity(
                                            EditRequiredInformationActivityHelper.getIntent(
                                                context
                                            )
                                        )
                                    },
                                    onNegative = { }
                                )
                                dialog.show(fragmentManager, dialog.tag)
                            }
                        }
                        // 승인요청 상태이면, 승인될 때까지 기다리라는 문구
                        RequestApproveCodeTable.code -> {
                            CustomDialog.newInstance(
                                DialogData.getWaitingUntilApprovalDialogData(context)
                            ).let { dialog ->
                                dialog.setButtonsClickListener(
                                    onPositive = { },
                                    onNegative = { }
                                )
                                dialog.show(fragmentManager, dialog.tag)
                            }
                        }
                        // 승인 상태이면, 문의 세부정보로 이동
                        else -> {
                            context.startActivity(
                                ViewRequirementActivityHelper.getIntent(
                                    context,
                                    requirementCard.id,
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun setCallToClientButton(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonLeft.isVisible = true
            buttonLeft.setText(R.string.call_to_customer_text)

            if (requirementCard.estimationDto?.fromMasterCallCnt!! > 0) {
                buttonLeft.setText(R.string.recall_to_customer_text)
                buttonLeft.setTextColor(context.resources.getColor(R.color.c_555555, null))
                buttonLeft.setBackgroundResource(R.drawable.shape_white_background_darkgray_border_radius8)
            }

            setLeftButtonClickListener {
                CustomDialog.newInstance(
                    DialogData.getCallToCustomerDialogData(context)
                ).let {
                    it.setButtonsClickListener(
                        onPositive = {
                            viewModel.callToClient(requirementId = requirementCard.id)
                            context.startActivity(CallToCustomerHelper.getIntent(
                                if (requirementCard.safetyNumber.isNullOrEmpty()) requirementCard.tel else requirementCard.safetyNumber))
                        },
                        onNegative = { }
                    )
                    it.show(fragmentManager, it.tag)
                }
            }
        }
    }

    fun setRepairDoneButtonConfirming(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonRight.isVisible = true
            buttonRight.setText(R.string.repair_done_text)
            setRightButtonClickListener {
                CustomDialog.newInstance(
                    dialogData = DialogData.getConfirmRepairDoneDialogData(context)
                ).let {
                    it.setButtonsClickListener(
                        onPositive = {
                            context.startActivity(
                                EndRepairActivityHelper.getIntent(
                                    context,
                                    requirementCard.id
                                )
                            )
                        },
                        onNegative = {}
                    )

                    it.show(fragmentManager, it.tag)
                }
            }
        }
    }

    fun setRepairDoneButton(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonRight.isVisible = true
            buttonRight.setText(R.string.repair_done_text)
            setRightButtonClickListener {
                context.startActivity(
                    EndRepairActivityHelper.getIntent(
                        context,
                        requirementCard.id
                    )
                )
            }
        }
    }

    fun setWriteEstimationButton(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonRight.isVisible = true
            buttonRight.setText(R.string.send_estimation)
            setRightButtonClickListener {
                context.startActivity(
                    if (requirementCard.typeCode == CompareCodeTable.code) {
                        EndRepairActivityHelper.getIntent(context, requirementCard.id)
                    } else {
                        if (requirementCard.status is RequirementStatus.Measuring) {
                            MeasureActivityHelper.getIntent(
                                context,
                                requirementCard.id
                            )
                        } else {
                            EndRepairActivityHelper.getIntent(
                                context,
                                requirementCard.id
                            )
                        }
                    }
                )
            }
        }
    }

    fun setRequestReviewButton(
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            buttonRight.isVisible = true
            buttonRight.isEnabled = true
            buttonRight.setText(R.string.requirements_card_review_button)
            setRightButtonClickListener { viewModel.askForReview(requirementCard) }

            requirementCard.estimationDto?.repair?.requestReviewYn?.let { requestReviewYn ->
                if (requestReviewYn) {
                    buttonRight.setText(R.string.ask_for_review_successful)
                    buttonRight.isEnabled = false
                }
            }
        }
    }

    companion object {
        const val THEME_BLUE = 1
        const val THEME_GREY = 2

        fun create(
            context: Context,
            fragmentManager: FragmentManager,
            viewModel: RequirementViewModel,
            parent: ViewGroup,
            viewType: Int,
        ): RequirementCardViewHolder {
            val binding = ViewHolderRequirementCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return when (viewType) {
                RequirementStatus.Requested.asInt -> RequestedCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)
                RequirementStatus.RequestConsult.asInt -> RequestConsultCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)
                RequirementStatus.RequestMeasure.asInt -> RequestMeasureCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)
                RequirementStatus.Estimated.asInt -> EstimatedCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Measuring.asInt -> MeasuringCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)
                RequirementStatus.Measured.asInt -> MeasuredCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)
                RequirementStatus.Repairing.asInt -> RepairingCardViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                RequirementStatus.Done.asInt -> DoneViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)
                RequirementStatus.Closed.asInt -> ClosedViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)
                RequirementStatus.Canceled.asInt -> CanceledViewHolder(context,
                    fragmentManager,
                    viewModel,
                    binding)

                else -> CanceledViewHolder(context, fragmentManager, viewModel, binding)
            }
        }
    }
}

