package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.CompareCodeTable
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.data.model.profile.SecretaryCodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.done.DoneViewModel
import kr.co.soogong.master.uihelper.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.MeasureActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.extension.formatFullDateTimeWithoutDay

// Requirement Card viewHolder 들의 부모클래스
open class RequirementCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            leftButton.isVisible = false
            rightButton.isVisible = false

            data = requirementCard

            setCardClickListener {
                context.startActivity(
                    ViewRequirementActivityHelper.getIntent(
                        context,
                        requirementCard.id
                    )
                )
            }

            // Note: additionalInfoContainer 에 있는 views 중복 방지
            if (additionalInfoContainer.isNotEmpty()) additionalInfoContainer.removeAllViews()
            measurementBadge.root.isVisible = requirementCard.typeCode == SecretaryCodeTable.code

            // 생성시간
            createdAt.text = requirementCard.createdAt.formatFullDateTimeWithoutDay()
        }
    }

    fun setApprovedMasterOnly(
        context: Context,
        fragmentManager: FragmentManager,
        binding: ViewHolderRequirementItemBinding,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            setCardClickListener {
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
        context: Context,
        fragmentManager: FragmentManager,
        binding: ViewHolderRequirementItemBinding,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            leftButton.isVisible = true
            leftButton.setText(R.string.call_to_customer_text)

            if (requirementCard.estimationDto?.fromMasterCallCnt!! > 0) {
                leftButton.setText(R.string.recall_to_customer_text)
                leftButton.setTextColor(context.resources.getColor(R.color.color_555555, null))
                leftButton.setBackgroundResource(R.drawable.shape_white_background_darkgray_border_radius8)
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
        context: Context,
        fragmentManager: FragmentManager,
        binding: ViewHolderRequirementItemBinding,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
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
        context: Context,
        fragmentManager: FragmentManager,
        binding: ViewHolderRequirementItemBinding,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
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
        context: Context,
        fragmentManager: FragmentManager,
        binding: ViewHolderRequirementItemBinding,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            rightButton.isVisible = true
            rightButton.setText(R.string.send_estimation)
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
        context: Context,
        fragmentManager: FragmentManager,
        binding: ViewHolderRequirementItemBinding,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            rightButton.isVisible = true
            rightButton.isEnabled = true
            rightButton.setText(R.string.requirements_card_review_button)
            setRightButtonClickListener { (viewModel as DoneViewModel).askForReview(requirementCard) }

            requirementCard.estimationDto?.repair?.requestReviewYn?.let { requestReviewYn ->
                if (requestReviewYn) {
                    rightButton.setText(R.string.ask_for_review_successful)
                    rightButton.isEnabled = false
                }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): RequirementCardViewHolder {
            val binding = ViewHolderRequirementItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return when (viewType) {
                RequirementStatus.Requested.asInt -> RequestedCardViewHolder(binding)
                RequirementStatus.RequestConsult.asInt -> RequestConsultCardViewHolder(binding)
                RequirementStatus.RequestMeasure.asInt -> RequestMeasureCardViewHolder(binding)
                RequirementStatus.Estimated.asInt -> EstimatedCardViewHolder(binding)

                RequirementStatus.Measuring.asInt -> MeasuringCardViewHolder(binding)
                RequirementStatus.Measured.asInt -> MeasuredCardViewHolder(binding)
                RequirementStatus.Repairing.asInt -> RepairingCardViewHolder(binding)
                RequirementStatus.RequestFinish.asInt -> RequestFinishCardViewHolder(binding)

                RequirementStatus.Done.asInt -> DoneViewHolder(binding)
                RequirementStatus.Closed.asInt -> ClosedViewHolder(binding)
                RequirementStatus.Canceled.asInt -> CanceledViewHolder(binding)

                else -> CanceledViewHolder(binding)
            }
        }
    }

    // TODO: 2021/10/13 버튼의 경우 상태에 따라 같은 버튼을 쓰는 경우가 있다. 이를 부모 뷰홀더에서 정의하고 사용하는 방법으로 리팩토링 해보자.
}

