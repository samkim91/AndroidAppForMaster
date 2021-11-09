package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.ui.CALENDAR_TYPE
import kr.co.soogong.master.ui.GRAY_THEME
import kr.co.soogong.master.ui.GREEN_THEME
import kr.co.soogong.master.ui.MONEY_TYPE
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo.Companion.setContainerTheme
import kr.co.soogong.master.utility.extension.formatDate
import kr.co.soogong.master.utility.extension.formatMoney

// 완료탭의 viewHolders

// 시공완료 상태
class DoneViewHolder(
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
            setRequestReviewButton(context, fragmentManager, binding, viewModel, requirementCard)

            setContainerTheme(context, additionalInfoContainer, GREEN_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GREEN_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_done_title),
                    contentData = requirementCard.estimationDto?.repair?.actualPrice.formatMoney(),
                    extraData = if (requirementCard.estimationDto?.repair?.actualPrice!! > 0) context.getString(
                        if (requirementCard.estimationDto.repair.includingVat == true) R.string.vat_included else R.string.vat_not_included) else "",
                    alertData = ""
                )
            })
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GREEN_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_done_date),
                    contentData = requirementCard.estimationDto?.repair?.actualDate.formatDate(),
                    extraData = "",
                    alertData = ""
                )
            })
        }
    }
}

// 평가완료 상태
class ClosedViewHolder(
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
            newBadge.visibility = View.GONE
            statusText.setTextColor(
                context.resources.getColor(
                    R.color.c_616161,
                    null
                )
            )

            setContainerTheme(context, additionalInfoContainer, GRAY_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GRAY_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_done_title),
                    contentData = requirementCard.estimationDto?.repair?.actualPrice.formatMoney(),
                    extraData = if (requirementCard.estimationDto?.repair?.actualPrice!! > 0) context.getString(
                        if (requirementCard.estimationDto.repair.includingVat == true) R.string.vat_included else R.string.vat_not_included) else "",
                    alertData = ""
                )
            })
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GRAY_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_done_date),
                    contentData = requirementCard.estimationDto?.repair?.actualDate.formatDate(),
                    extraData = "",
                    alertData = ""
                )
            })
        }
    }
}

// 시공취소 상태
class CanceledViewHolder(
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
            newBadge.visibility = View.GONE
            statusText.setTextColor(
                context.resources.getColor(
                    R.color.c_616161,
                    null
                )
            )
        }
    }
}

