package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
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

// 완료탭의 viewHolders

// 시공완료 상태
class DoneViewHolder(
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
            setContainerTheme(context, additionalInfoContainer, GREEN_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GREEN_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_done_title),
                    contentData = "${moneyFormat.format(requirementCard.estimationDto?.repair?.actualPrice)}원",
                    alertData = ""
                )
            })
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GREEN_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_done_date),
                    contentData = dateFormatWithoutHour.format(requirementCard.estimationDto?.repair?.actualDate),
                    alertData = ""
                )
            })

            rightButton.isVisible = true
            rightButton.isEnabled = true
            rightButton.setText(R.string.requirements_card_review_button)
            setRightButtonClickListener { viewModel.askForReview(requirementCard) }

            requirementCard.estimationDto?.repair?.requestReviewYn?.let { requestReviewYn ->
                if (requestReviewYn) {
                    rightButton.setText(R.string.ask_for_review_successful)
                    rightButton.isEnabled = false
                }
            }
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
                    titleData = context.getString(R.string.requirements_card_amount_done_title),
                    contentData = "${moneyFormat.format(requirementCard.estimationDto?.repair?.actualPrice)}원",
                    alertData = ""
                )
            })
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GRAY_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_done_date),
                    contentData = dateFormatWithoutHour.format(requirementCard.estimationDto?.repair?.actualDate),
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
        }
    }
}

