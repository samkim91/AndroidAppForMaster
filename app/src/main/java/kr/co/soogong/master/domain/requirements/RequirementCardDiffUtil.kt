package kr.co.soogong.master.domain.requirements

import androidx.recyclerview.widget.DiffUtil

class RequirementCardDiffUtil : DiffUtil.ItemCallback<RequirementCard>() {
    override fun areItemsTheSame(oldItem: RequirementCard, newItem: RequirementCard): Boolean {
        return oldItem.keyCode == newItem.keyCode
    }

    override fun areContentsTheSame(oldItem: RequirementCard, newItem: RequirementCard): Boolean {
        return oldItem == newItem
    }
}