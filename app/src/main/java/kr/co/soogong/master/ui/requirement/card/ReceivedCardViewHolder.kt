package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.ui.CALENDAR_TYPE
import kr.co.soogong.master.ui.GRAY_THEME
import kr.co.soogong.master.ui.MONEY_TYPE
import kr.co.soogong.master.ui.ORANGE_THEME
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo.Companion.setContainerTheme
import kr.co.soogong.master.utility.TimeHelper
import kr.co.soogong.master.utility.TimeHelper.WITHIN_24_HOURS
import kr.co.soogong.master.utility.TimeHelper.WITHIN_90_MINUTES
import kr.co.soogong.master.utility.extension.formatMoney

// 문의탭의 viewHolders

// 견적요청 상태
class RequestedCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            setApprovedMasterOnly(context, fragmentManager, binding, viewModel, requirementCard)

            setContainerTheme(context, additionalInfoContainer, ORANGE_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = ORANGE_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_due_date),
                    contentData = TimeHelper.getDueTime(requirementCard.createdAt, WITHIN_24_HOURS),
                    extraData = "",
                    alertData = context.getString(R.string.requirements_card_due_date_alert)
                )
            })
        }
    }
}

// 상담요청 상태
class RequestConsultCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            setApprovedMasterOnly(context, fragmentManager, binding, viewModel, requirementCard)

            setCallToClientButton(context, fragmentManager, binding, viewModel, requirementCard)
            setRepairDoneButtonConfirming(context,
                fragmentManager,
                binding,
                viewModel,
                requirementCard)

            // 견적을 냈을 때의 layout
            if (requirementCard.estimationDto?.masterResponseCode == EstimationResponseCode.ACCEPTED) {
                setContainerTheme(context, additionalInfoContainer, GRAY_THEME)
                additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                    setLayout(
                        theme = GRAY_THEME,
                        type = MONEY_TYPE,
                        titleData = context.getString(R.string.requirements_card_amount_title),
                        contentData = if (requirementCard.estimationDto.price!! > 0) requirementCard.estimationDto.price.formatMoney() else context.getString(
                            R.string.not_estimated_text),
                        extraData = if (requirementCard.estimationDto.price > 0) context.getString(
                            if (requirementCard.estimationDto.includingVat == true) R.string.vat_included else R.string.vat_not_included) else "",
                        alertData = context.getString(R.string.requirements_card_waiting_label),
                    )
                })
                return
            }

            // 견적을 안 냈을 때의 layout
            setContainerTheme(context, additionalInfoContainer, ORANGE_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = ORANGE_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_due_date),
                    contentData = TimeHelper.getDueTime(requirementCard.createdAt, WITHIN_24_HOURS),
                    extraData = "",
                    alertData = context.getString(R.string.requirements_card_due_date_alert)
                )
            })
        }
    }
}

// 실측요청 상태
class RequestMeasureCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            setApprovedMasterOnly(context, fragmentManager, binding, viewModel, requirementCard)

            setContainerTheme(context, additionalInfoContainer, ORANGE_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = ORANGE_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_due_time),
                    contentData = TimeHelper.getDueTime(requirementCard.estimationDto?.createdAt,
                        WITHIN_90_MINUTES),
                    extraData = "",
                    alertData = context.getString(R.string.requirements_card_due_time_alert)
                )
            })
        }
    }
}

// 매칭대기 상태
class EstimatedCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        with(binding) {
            setApprovedMasterOnly(context, fragmentManager, binding, viewModel, requirementCard)

            if (!requirementCard.safetyNumber.isNullOrEmpty()) setCallToClientButton(context,
                fragmentManager,
                binding,
                viewModel,
                requirementCard)
            setRepairDoneButtonConfirming(context,
                fragmentManager,
                binding,
                viewModel,
                requirementCard)

            setContainerTheme(context, additionalInfoContainer, GRAY_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GRAY_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_title),
                    contentData = requirementCard.estimationDto?.price?.let {
                        if (it > 0) it.formatMoney() else context.getString(R.string.not_estimated_text)
                    },
                    extraData = if (requirementCard.estimationDto?.price!! > 0) context.getString(if (requirementCard.estimationDto.includingVat == true) R.string.vat_included else R.string.vat_not_included) else "",
                    alertData = context.getString(R.string.requirements_card_waiting_label),
                )
            })
        }
    }
}