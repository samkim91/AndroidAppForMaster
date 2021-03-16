package kr.co.soogong.master.ui.requirements.card

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.domain.requirements.RequirementCard

abstract class EstimationCardViewHolder(
    open val binding: ViewHolderRequirementItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    open fun binding(
        requirementCard: RequirementCard,
        cardClickClickListener: ((String) -> Unit),
        leftButtonClickListener: ((String) -> Unit)? = null,
        rightButtonClickListener: ((String) -> Unit)? = null
    ) {
        with(binding) {
            vm = requirementCard

            setCardClickListener {
                cardClickClickListener(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}

class RequestViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : EstimationCardViewHolder(binding) {

    override fun binding(
        requirementCard: RequirementCard,
        cardClickClickListener: ((String) -> Unit),
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        with(binding) {
            vm = requirementCard

            endTime.visibility = View.VISIBLE
            endTime.setEndDate(requirementCard.createdAt)

            setCardClickListener {
                cardClickClickListener(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}

class WaitingViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickClickListener: ((String) -> Unit),
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        with(binding) {
            vm = requirementCard

            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status, requirementCard.transmissions)

            waitingLabel.visibility = View.VISIBLE

            setCardClickListener {
                cardClickClickListener(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}

class ProgressViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickClickListener: ((String) -> Unit),
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        with(binding) {
            vm = requirementCard

            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status, requirementCard.transmissions)

            callButton.visibility = View.VISIBLE
            doneButton.visibility = View.VISIBLE

            setCardClickListener {
                cardClickClickListener(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}

class CustomDoneViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickClickListener: ((String) -> Unit),
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        with(binding) {
            vm = requirementCard

            doneButton.visibility = View.VISIBLE

            setCardClickListener {
                cardClickClickListener(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}

class DoneViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickClickListener: ((String) -> Unit),
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        with(binding) {
            vm = requirementCard

            reviewButton.visibility = View.VISIBLE

            setCardClickListener {
                cardClickClickListener(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}

class FinalViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickClickListener: ((String) -> Unit),
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        with(binding) {
            vm = requirementCard

            newBadge.visibility = View.GONE
            statusText.setTextColor(
                ResourcesCompat.getColor(
                    root.resources,
                    R.color.color_616161,
                    null
                )
            )
            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status, requirementCard.transmissions)

            setCardClickListener {
                cardClickClickListener(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}

class CancelViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickClickListener: ((String) -> Unit),
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        with(binding) {
            vm = requirementCard

            newBadge.visibility = View.GONE
            statusText.setTextColor(
                ResourcesCompat.getColor(
                    root.resources,
                    R.color.color_616161,
                    null
                )
            )

            setCardClickListener {
                cardClickClickListener(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}
