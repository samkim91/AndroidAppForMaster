package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.NotApprovedCodeTable
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.ui.CALENDAR_TYPE
import kr.co.soogong.master.ui.GRAY_THEME
import kr.co.soogong.master.ui.MONEY_TYPE
import kr.co.soogong.master.ui.ORANGE_THEME
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo.Companion.setContainerTheme
import kr.co.soogong.master.uihelper.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.extension.getEstimationDueDate

// 문의탭의 viewHolders

// 문의탭의 부모 viewHolder
open class ReceivedCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            setCardClickListener {
                viewModel.masterSimpleInfo.value?.approvedStatus.let {
                    when (it) {
                        // 미승인 상태이면, 필수정보를 채우도록 이동
                        NotApprovedCodeTable.code -> {
                            val dialog =
                                CustomDialog.newInstance(
                                    DialogData.getAskingFillProfileDialogData(context),
                                    yesClick = {
                                        context.startActivity(
                                            EditRequiredInformationActivityHelper.getIntent(
                                                context
                                            )
                                        )
                                    },
                                    noClick = { })
                            dialog.show(fragmentManager, dialog.tag)
                        }
                        // 승인요청 상태이면, 승인될 때까지 기다리라는 문구
                        RequestApproveCodeTable.code -> {
                            val dialog =
                                CustomDialog.newInstance(
                                    DialogData.getWaitingUntilApprovalDialogData(
                                        context
                                    ),
                                    yesClick = { },
                                    noClick = { })
                            dialog.show(fragmentManager, dialog.tag)
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

            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
            setRightButtonClickListener {
                CustomDialog.newInstance(
                    dialogData = DialogData.getConfirmRepairDoneDialogData(context),
                    yesClick = {
                        context.startActivity(
                            EndRepairActivityHelper.getIntent(
                                context,
                                requirementCard.id
                            )
                        )
                    },
                    noClick = {}
                ).run {
                    show(fragmentManager, this.tag)
                }
            }
        }
    }
}

// 견적요청 상태
class RequestedCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : ReceivedCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            setContainerTheme(context, additionalInfoContainer, ORANGE_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = ORANGE_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_due_date),
                    contentData = dateFormat.format(getEstimationDueDate(requirementCard.createdAt)),
                    alertData = context.getString(R.string.requirements_card_due_date_alert)
                )
            })

            rightButton.isVisible = false
        }
    }
}

// 매칭대기 상태
class EstimatedCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : ReceivedCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            setContainerTheme(context, additionalInfoContainer, GRAY_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GRAY_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_title),
                    contentData = if (requirementCard.estimationDto?.price!! > 0)
                        "${moneyFormat.format(requirementCard.estimationDto.price)}원" else context.getString(
                        R.string.not_estimated_text
                    ),
                    alertData = context.getString(R.string.requirements_card_waiting_label),
                )
            })
        }
    }
}

// 상담요청 상태
class RequestConsultCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : ReceivedCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            if (requirementCard.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED) {
                setContainerTheme(context, additionalInfoContainer, GRAY_THEME)
                additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                    setLayout(
                        theme = GRAY_THEME,
                        type = MONEY_TYPE,
                        titleData = context.getString(R.string.requirements_card_amount_title),
                        contentData = if (requirementCard.estimationDto.price!! > 0) "${
                            moneyFormat.format(
                                requirementCard.estimationDto.price
                            )
                        }원" else context.getString(R.string.not_estimated_text),
                        alertData = context.getString(R.string.requirements_card_waiting_label),
                    )
                })
                return
            }

            setContainerTheme(context, additionalInfoContainer, ORANGE_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = ORANGE_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_due_date),
                    contentData = dateFormat.format(getEstimationDueDate(requirementCard.createdAt)),
                    alertData = context.getString(R.string.requirements_card_due_date_alert)
                )
            })
        }
    }
}