package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.ui.GRAY_THEME
import kr.co.soogong.master.ui.GREEN_THEME
import kr.co.soogong.master.ui.MONEY_TYPE
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo.Companion.setContainerTheme
import kr.co.soogong.master.utility.extension.formatMoney

// 진행중탭의 viewHolders

// 실측예정 상태
class MeasuringCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        super.bind(context, fragmentManager, viewModel, requirementCard)

        setCallToClientButton(context, fragmentManager, binding, viewModel, requirementCard)
        setWriteEstimationButton(context, fragmentManager, binding, viewModel, requirementCard)
    }
}

// 실측완료 상태
class MeasuredCardViewHolder(
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
                    R.color.color_616161,
                    null
                )
            )

            setCallToClientButton(context, fragmentManager, binding, viewModel, requirementCard)
            setRepairDoneButton(context, fragmentManager, binding, viewModel, requirementCard)

            setContainerTheme(context, additionalInfoContainer, GRAY_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GRAY_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_title),
                    contentData = requirementCard.estimationDto?.price.formatMoney(),
                    extraData = context.getString(if (requirementCard.estimationDto?.includingVat == true) R.string.vat_included else R.string.vat_not_included),
                    alertData = ""
                )
            })
        }
    }
}

// 시공예정 상태
class RepairingCardViewHolder(
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
            setCallToClientButton(context, fragmentManager, binding, viewModel, requirementCard)
            setRepairDoneButton(context, fragmentManager, binding, viewModel, requirementCard)

            setContainerTheme(context, additionalInfoContainer, GREEN_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GREEN_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_title),
                    contentData = if (requirementCard.estimationDto?.price!! > 0) requirementCard.estimationDto.price.formatMoney() else context.getString(
                        R.string.not_estimated_text),
                    extraData = if (requirementCard.estimationDto.price > 0) context.getString(if (requirementCard.estimationDto.includingVat == true) R.string.vat_included else R.string.vat_not_included) else "",
                    alertData = ""
                )
            })
        }
    }
}

// 고객완료요청 상태 : 현재는 사용하는 곳이 없다..
class RequestFinishCardViewHolder(
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
            leftButton.isVisible = false
        }
    }
}