package kr.co.soogong.master.ui.requirements.card

import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding

abstract class RequirementViewHolder(
    open val binding: ViewHolderRequirementItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun binding(
        requirementCard: RequirementCard,
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    )
}

class RequestViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : RequirementViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}

class WaitingViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : RequirementViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}

class ProgressViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : RequirementViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}

class CustomDoneViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : RequirementViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}

class DoneViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : RequirementViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}

class FinalViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : RequirementViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}

class CancelViewHolder(
    override val binding: ViewHolderRequirementItemBinding
) : RequirementViewHolder(binding) {
    override fun binding(
        requirementCard: RequirementCard,
        leftButtonClickListener: ((String) -> Unit)?,
        rightButtonClickListener: ((String) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}
