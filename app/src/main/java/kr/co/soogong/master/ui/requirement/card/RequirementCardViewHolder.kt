package kr.co.soogong.master.ui.requirement.card

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding

// Requirement Card viewHolder 들의 추상클래스
abstract class RequirementCardViewHolder(
    open val binding: ViewHolderRequirementItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    open fun binding(
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

            executePendingBindings()
        }
    }
}

// 견적요청 상태
class RequestedViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            firstDate.setFirstDate(requirementCard.status?.toString(), requirementCard.createdAt)
            secondDate.visibility = View.VISIBLE
            secondDate.setSecondDate(requirementCard.createdAt)

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            executePendingBindings()
        }
    }
}

// 매칭대기 상태
class EstimatedViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            firstDate.setFirstDate(requirementCard.status?.toString(), requirementCard.createdAt)
            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status?.toString(), requirementCard.estimationDto?.price)

            waitingLabel.visibility = View.VISIBLE

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            executePendingBindings()
        }
    }
}

// 시공진행중 상태
class RepairingViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            firstDate.setFirstDate(requirementCard.status?.toString(), requirementCard.estimationDto?.updatedAt)
            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status?.toString(), requirementCard.estimationDto?.price)

            callButton.visibility = View.VISIBLE
            doneButton.visibility = View.VISIBLE

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            // TODO.. 첫 전화인지, 아닌지에 따라 버튼의 UI를 변경해줘야햠. Figma 참고
            callButton.setOnClickListener {
                leftButtonClickListener?.invoke(requirementCard.id, requirementCard.tel)
            }

            doneButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.id, requirementCard)
            }

            executePendingBindings()
        }
    }
}

// 고객완료요청 상태
class RequestFinishViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            firstDate.setFirstDate(requirementCard.status?.toString(), requirementCard.estimationDto?.updatedAt)
            doneButton.visibility = View.VISIBLE

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            doneButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.id, requirementCard)
            }

            executePendingBindings()
        }
    }
}

// 시공완료 상태
class DoneViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            firstDate.setFirstDate(requirementCard.status?.toString(), requirementCard.estimationDto?.repair?.createdAt)

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            reviewButton.visibility = View.VISIBLE
            reviewButton.setText(R.string.requirements_card_review_button)
            reviewButton.isEnabled = true

            reviewButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.id, requirementCard)
            }

            requirementCard.estimationDto?.repair?.requestReviewYn?.let { requestReviewYn ->
                if (requestReviewYn) {
                    reviewButton.setText(R.string.ask_for_review_successful)
                    reviewButton.isEnabled = false
                }
            }

            executePendingBindings()
        }
    }
}

// 평가완료 상태
class ClosedViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            newBadge.visibility = View.GONE
            statusText.setTextColor(
                ResourcesCompat.getColor(
                    root.resources,
                    R.color.color_616161,
                    null
                )
            )
            firstDate.setFirstDate(requirementCard.status?.toString(), requirementCard.estimationDto?.repair?.actualDate)

            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status?.toString(), requirementCard.estimationDto?.repair?.actualPrice)

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            executePendingBindings()
        }
    }
}

// 시공취소 상태
class CanceledViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : RequirementCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            newBadge.visibility = View.GONE
            statusText.setTextColor(
                ResourcesCompat.getColor(
                    root.resources,
                    R.color.color_616161,
                    null
                )
            )

            firstDate.setFirstDate(requirementCard.status?.toString(), requirementCard.createdAt)

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            executePendingBindings()
        }
    }
}
