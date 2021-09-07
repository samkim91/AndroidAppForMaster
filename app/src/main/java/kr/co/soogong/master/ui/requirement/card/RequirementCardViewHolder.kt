package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.SecretaryCodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
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
    val moneyFormat = DecimalFormat("#,###")

    open fun bind(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)? = null,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)? = null,
    ) {
        with(binding) {
            data = requirementCard

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

        with(binding) {
            setContainerTheme(context, additionalInfoContainer, GRAY_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GRAY_THEME,
                    type = MONEY_TYPE,
                    titleData = context.getString(R.string.requirements_card_amount_title),
                    contentData = if (requirementCard.estimationDto?.price!! > 0) "${
                        moneyFormat.format(
                            requirementCard.estimationDto.price
                        )
                    }원" else context.getString(R.string.not_estimated_text),
                    alertData = context.getString(R.string.requirements_card_waiting_label),
                )
            })
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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

        with(binding) {
            setContainerTheme(context, additionalInfoContainer, GREEN_THEME)
            additionalInfoContainer.addView(RequirementCardAdditionalInfo(context).apply {
                setLayout(
                    theme = GREEN_THEME,
                    type = CALENDAR_TYPE,
                    titleData = context.getString(R.string.requirements_card_measuring_date),
                    contentData = requirementCard.measuringDate?.let { dateFormat.format(it) },
                    alertData = ""
                )

                leftButton.isVisible = true
                rightButton.isVisible = true
                leftButton.setText(R.string.call_to_customer_text)
                rightButton.setText(R.string.send_estimation)

                // TODO.. 첫 전화인지, 아닌지에 따라 버튼의 UI를 변경해줘야햠. Figma 참고
                leftButton.setOnClickListener {
                    leftButtonClickListener?.invoke(requirementCard.id, requirementCard.tel)
                }

                rightButton.setOnClickListener {
                    rightButtonClickListener?.invoke(requirementCard.id, requirementCard)
                }
            })
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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

        with(binding) {
            newBadge.visibility = View.GONE
            statusText.setTextColor(
                ResourcesCompat.getColor(
                    root.resources,
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

            // TODO.. 첫 전화인지, 아닌지에 따라 버튼의 UI를 변경해줘야햠. Figma 참고
            leftButton.setOnClickListener {
                leftButtonClickListener?.invoke(requirementCard.id, requirementCard.tel)
            }
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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

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
            rightButton.isVisible = true
            leftButton.setText(R.string.call_to_customer_text)
            rightButton.setText(R.string.repair_done_text)

            // TODO.. 첫 전화인지, 아닌지에 따라 버튼의 UI를 변경해줘야햠. Figma 참고
            leftButton.setOnClickListener {
                leftButtonClickListener?.invoke(requirementCard.id, requirementCard.tel)
            }

            rightButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.id, requirementCard)
            }
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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

        with(binding) {
            rightButton.isVisible = true
            rightButton.setText(R.string.repair_done_text)

            rightButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.id, requirementCard)
            }
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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

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
                    contentData = dateFormat.format(requirementCard.estimationDto?.repair?.actualDate),
                    alertData = ""
                )
            })

            rightButton.isVisible = true
            rightButton.setText(R.string.requirements_card_review_button)

            rightButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.id, requirementCard)
            }

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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

        with(binding) {
            newBadge.visibility = View.GONE
            statusText.setTextColor(
                ResourcesCompat.getColor(
                    root.resources,
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
                    contentData = dateFormat.format(requirementCard.estimationDto?.repair?.actualDate),
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
        cardClickListener: (requirementId: Int) -> Unit,
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?
    ) {
        super.bind(
            requirementCard,
            cardClickListener,
            leftButtonClickListener,
            rightButtonClickListener
        )

        with(binding) {
            newBadge.visibility = View.GONE
            statusText.setTextColor(
                ResourcesCompat.getColor(
                    root.resources,
                    R.color.color_616161,
                    null
                )
            )
        }
    }
}

