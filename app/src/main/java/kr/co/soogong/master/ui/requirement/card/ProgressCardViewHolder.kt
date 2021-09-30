package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.CompareCodeTable
import kr.co.soogong.master.data.model.requirement.Measuring
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.ui.GRAY_THEME
import kr.co.soogong.master.ui.GREEN_THEME
import kr.co.soogong.master.ui.MONEY_TYPE
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.requirement.progress.ProgressViewModel
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo.Companion.setContainerTheme
import kr.co.soogong.master.uihelper.requirment.CallToCustomerHelper
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.MeasureActivityHelper

// 진행중탭의 viewHolders

// 진행중탭의 부모 viewHolder
open class ProgressCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: BaseViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

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
                    DialogData.getCallToCustomerDialogData(context),
                    yesClick = {
                        (viewModel as ProgressViewModel).callToClient(requirementId = requirementCard.id)
                        context.startActivity(CallToCustomerHelper.getIntent(requirementCard.tel.toString()))
                    },
                    noClick = { }
                ).run {
                    this.show(fragmentManager, this.tag)
                }
            }

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
}

// 실측예정 상태
class MeasuringCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : ProgressCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: BaseViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            rightButton.isVisible = true
            rightButton.setText(R.string.send_estimation)
            setRightButtonClickListener {
                context.startActivity(
                    if (requirementCard.typeCode == CompareCodeTable.code) {
                        EndRepairActivityHelper.getIntent(context, requirementCard.id)
                    } else {
                        if (requirementCard.status == Measuring) {
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
}

// 실측완료 상태
class MeasuredCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : ProgressCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: BaseViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            newBadge.visibility = View.GONE
            statusText.setTextColor(
                context.resources.getColor(
                    R.color.color_616161,
                    null
                )
            )

            setContainerTheme(context, additionalInfoContainer, GRAY_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GRAY_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_title),
                    contentData = "${moneyFormat.format(requirementCard.estimationDto?.price)}원",
                    alertData = ""
                )
            })
        }
    }
}

// 시공예정 상태
class RepairingCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : ProgressCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: BaseViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            setContainerTheme(context, additionalInfoContainer, GREEN_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GREEN_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_title),
                    contentData = if (requirementCard.estimationDto?.price!! > 0) "${
                        moneyFormat.format(
                            requirementCard.estimationDto.price
                        )
                    }원" else context.getString(R.string.not_estimated_text),
                    alertData = ""
                )
            })
        }
    }
}

// 고객완료요청 상태
class RequestFinishCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : ProgressCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: BaseViewModel,
        requirementCard: RequirementCard
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            leftButton.isVisible = false
        }
    }
}