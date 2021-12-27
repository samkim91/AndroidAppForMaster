package kr.co.soogong.master.ui.requirement.card

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.model.requirement.RequirementCardV2

class RequirementCardDiffUtil : DiffUtil.ItemCallback<RequirementCardV2>() {
    override fun areItemsTheSame(oldItem: RequirementCardV2, newItem: RequirementCardV2): Boolean {
        return oldItem.token == newItem.token
    }

    override fun areContentsTheSame(
        oldItem: RequirementCardV2,
        newItem: RequirementCardV2,
    ): Boolean {
        return oldItem == newItem
    }
}