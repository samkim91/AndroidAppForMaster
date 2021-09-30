package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.SecretaryCodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.ui.*
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo
import kr.co.soogong.master.ui.widget.RequirementCardAdditionalInfo.Companion.setContainerTheme
import kr.co.soogong.master.utility.extension.getEstimationDueDate
import java.util.*

// Requirement Card viewHolder 들의 추상클래스
abstract class RequirementCardViewHolder(
    open val context: Context,
    open val binding: ViewHolderRequirementItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    val dateFormat = SimpleDateFormat("yyyy.MM.dd(E) - HH:mm", Locale.KOREA)
    val dateFormatWithoutHour = SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREA)
    val moneyFormat = DecimalFormat("#,###")

    open fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit,
    ) {
        with(binding) {
            data = requirementCard

            setCardClickListener { onCardClicked(requirementCard.id) }
            setLeftButtonClickListener { onLeftButtonClicked(requirementCard) }
            setRightButtonClickListener { onRightButtonClicked(requirementCard) }

            // Note: additionalInfoContainer 에 있는 views 중복 방지
            if (additionalInfoContainer.isNotEmpty()) additionalInfoContainer.removeAllViews()
            measurementBadge.root.isVisible = requirementCard.typeCode == SecretaryCodeTable.code
        }
    }
}

// 견적요청 상태
class RequestedViewHolder(
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit,
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

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

            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
        }
    }
}

// 매칭대기 상태
class EstimatedViewHolder(
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit,
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

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

            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
        }
    }
}

// 상담요청 상태
class RequestConsultViewHolder(
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

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

            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
        }
    }
}

// 실측예정 상태
class MeasuringViewHolder(
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

        with(binding) {
            leftButton.isVisible = true
            rightButton.isVisible = true
            leftButton.setText(R.string.call_to_customer_text)
            rightButton.setText(R.string.send_estimation)

            if (requirementCard.estimationDto?.fromMasterCallCnt!! > 0) {
                leftButton.setText(R.string.recall_to_customer_text)
                leftButton.setTextColor(context.resources.getColor(R.color.color_555555, null))
                leftButton.setBackgroundResource(R.drawable.shape_white_background_darkgray_border_radius8)
            }
        }
    }
}

// 실측완료 상태
class MeasuredViewHolder(
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

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

            leftButton.isVisible = true
            leftButton.setText(R.string.call_to_customer_text)
            if (requirementCard.estimationDto?.fromMasterCallCnt!! > 0) {
                leftButton.setText(R.string.recall_to_customer_text)
                leftButton.setTextColor(context.resources.getColor(R.color.color_555555, null))
                leftButton.setBackgroundResource(R.drawable.shape_white_background_darkgray_border_radius8)
            }

            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
        }
    }
}

// 시공예정 상태
class RepairingViewHolder(
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

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

            leftButton.isVisible = true
            leftButton.setText(R.string.call_to_customer_text)
            if (requirementCard.estimationDto?.fromMasterCallCnt!! > 0) {
                leftButton.setText(R.string.recall_to_customer_text)
                leftButton.setTextColor(context.resources.getColor(R.color.color_555555, null))
                leftButton.setBackgroundResource(R.drawable.shape_white_background_darkgray_border_radius8)
            }

            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
        }
    }
}

// 고객완료요청 상태
class RequestFinishViewHolder(
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

        with(binding) {
            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)
        }
    }
}

// 시공완료 상태
class DoneViewHolder(
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

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
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

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
    override val context: Context,
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(context, binding) {
    override fun bind(
        requirementCard: RequirementCard,
        onCardClicked: (requirementId: Int) -> Unit,
        onLeftButtonClicked: (requirementCard: RequirementCard) -> Unit,
        onRightButtonClicked: (requirementCard: RequirementCard) -> Unit
    ) {
        super.bind(requirementCard, onCardClicked, onLeftButtonClicked, onRightButtonClicked)

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

