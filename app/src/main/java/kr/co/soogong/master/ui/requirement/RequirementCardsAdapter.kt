package kr.co.soogong.master.ui.requirement

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolder
import kr.co.soogong.master.ui.requirement.card.RequirementCardDiffUtil
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolderHelper

class RequirementCardsAdapter(
    private val onCardClicked: (requirementId: Int) -> Unit,
    private val onLeftButtonClicked: (requirementId: RequirementCard) -> Unit,
    private val onRightButtonClicked: (requirementId: RequirementCard) -> Unit
) : ListAdapter<RequirementCard, RequirementCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: RequirementCardViewHolder, position: Int) {
        holder.bind(currentList[position], onCardClicked, onLeftButtonClicked, onRightButtonClicked)
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].status?.asInt!!
}