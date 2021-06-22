package kr.co.soogong.master.ui.requirement.card

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding

// Requirement Card viewHolder 들의 추상클래스
abstract class EstimationCardViewHolder(
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
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            endTime.visibility = View.VISIBLE
            endTime.setEndDatetime(requirementCard.createdAt)

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
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status, requirementCard.estimationDto?.price.toString())

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
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status, requirementCard.estimationDto?.price.toString())

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
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

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
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((requirementId: Int) -> Unit),
        leftButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
        rightButtonClickListener: ((requirementId: Int, extraInfo: Any?) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            reviewButton.visibility = View.VISIBLE

            reviewButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.id, requirementCard)
            }

            requirementCard.estimationDto?.repair?.requestReviewYn?.let { boolean ->
                if (boolean) {
                    reviewButton.setText(R.string.ask_for_review_successful)
                    reviewButton.setBackgroundResource(R.color.color_90E9BD)
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
) : EstimationCardViewHolder(binding) {
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
            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status, requirementCard.estimationDto?.price.toString())

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
) : EstimationCardViewHolder(binding) {
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

            setCardClickListener {
                cardClickListener(requirementCard.id)
            }

            executePendingBindings()
        }
    }
}
