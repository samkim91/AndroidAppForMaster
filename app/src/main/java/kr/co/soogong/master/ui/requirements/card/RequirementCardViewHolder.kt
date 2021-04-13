package kr.co.soogong.master.ui.requirements.card

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.requirements.RequirementCard

abstract class EstimationCardViewHolder(
    open val binding: ViewHolderRequirementItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    open fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((keycode: String, status: EstimationStatus) -> Unit),
        leftButtonClickListener: ((keycode: String, phoneNumber: String) -> Unit)? = null,
        rightButtonClickListener: ((String) -> Unit)? = null,
    ) {
        with(binding) {
            data = requirementCard

            setCardClickListener {
                cardClickListener(requirementCard.keyCode, requirementCard.status)
            }

            executePendingBindings()
        }
    }
}

// 견적요청 상태
class RequestViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : EstimationCardViewHolder(binding) {

    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((String, EstimationStatus) -> Unit),
        leftButtonClickListener: ((String, String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            endTime.visibility = View.VISIBLE
            endTime.setEndDate(requirementCard.createdAt)

            setCardClickListener {
                cardClickListener(requirementCard.keyCode, requirementCard.status)
            }

            executePendingBindings()
        }
    }
}

// 매칭대기 상태
class WaitingViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((String, EstimationStatus) -> Unit),
        leftButtonClickListener: ((String, String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status, requirementCard.transmissions)

            waitingLabel.visibility = View.VISIBLE

            setCardClickListener {
                cardClickListener(requirementCard.keyCode, requirementCard.status)
            }

            executePendingBindings()
        }
    }
}

// 시공진행중 상태
class ProgressViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((String, EstimationStatus) -> Unit),
        leftButtonClickListener: ((String, String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            myAmount.visibility = View.VISIBLE
            myAmount.setAmount(requirementCard.status, requirementCard.transmissions)

            callButton.visibility = View.VISIBLE
            doneButton.visibility = View.VISIBLE

            setCardClickListener {
                cardClickListener(requirementCard.keyCode, requirementCard.status)
            }

            // TODO.. 첫 전화인지, 아닌지에 따라 버튼의 UI를 변경해줘야햠. Figma 참고
            callButton.setOnClickListener {
                // Todo.. Requirement card가 phoneNum을 포함해야함.
                leftButtonClickListener?.invoke(requirementCard.keyCode, "requirementCard.phoneNum")
            }

            doneButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.keyCode)
            }

            executePendingBindings()
        }
    }
}

// 고객완료요청 상태
class CustomDoneViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((String, EstimationStatus) -> Unit),
        leftButtonClickListener: ((String, String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            doneButton.visibility = View.VISIBLE

            setCardClickListener {
                cardClickListener(requirementCard.keyCode, requirementCard.status)
            }

            doneButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.keyCode)
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
        cardClickListener: ((String, EstimationStatus) -> Unit),
        leftButtonClickListener: ((String, String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?,
    ) {
        with(binding) {
            data = requirementCard

            setCardClickListener {
                cardClickListener(requirementCard.keyCode, requirementCard.status)
            }

            reviewButton.visibility = View.VISIBLE

            reviewButton.setOnClickListener {
                rightButtonClickListener?.invoke(requirementCard.keyCode)
            }
            // Todo.. review 요청이 이미 완료된 상태라면, Disabled button으로 변경하고, "리뷰 요청을 완료했어요!"로 문구룰 바꿔야함.
            if(requirementCard.status.equals("askedReview")){
                reviewButton.setText(R.string.ask_for_review_successful)
                reviewButton.setBackgroundResource(R.color.color_90E9BD)
                reviewButton.setOnClickListener { null }
            }

            executePendingBindings()
        }
    }
}

// 평가완료 상태
class FinalViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((String, EstimationStatus) -> Unit),
        leftButtonClickListener: ((String, String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?,
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
            myAmount.setAmount(requirementCard.status, requirementCard.transmissions)

            setCardClickListener {
                cardClickListener(requirementCard.keyCode, requirementCard.status)
            }

            executePendingBindings()
        }
    }
}

// 시공취소 상태
class CancelViewHolder(
    override val binding: ViewHolderRequirementItemBinding,
) : EstimationCardViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        cardClickListener: ((String, EstimationStatus) -> Unit),
        leftButtonClickListener: ((String, String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?,
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
                cardClickListener(requirementCard.keyCode, requirementCard.status)
            }

            executePendingBindings()
        }
    }
}
