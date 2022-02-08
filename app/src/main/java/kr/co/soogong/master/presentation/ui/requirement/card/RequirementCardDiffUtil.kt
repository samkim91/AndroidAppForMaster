package kr.co.soogong.master.presentation.ui.requirement.card

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.domain.entity.requirement.RequirementCard

class RequirementCardDiffUtil : DiffUtil.ItemCallback<RequirementCard>() {
    override fun areItemsTheSame(oldItem: RequirementCard, newItem: RequirementCard): Boolean {
        return oldItem.token == newItem.token
    }

    override fun areContentsTheSame(
        oldItem: RequirementCard,
        newItem: RequirementCard,
    ): Boolean {
        return oldItem == newItem
    }
}