package kr.co.soogong.master.ui.requirement.received

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolder
import kr.co.soogong.master.ui.requirement.card.RequirementCardDiffUtil
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolderHelper

class ReceivedAdapter(
    private val cardClickListener: (Int) -> Unit,
    private val leftButtonClick: (Int, Any?) -> Unit,
    private val rightButtonClick: (Int, Any?) -> Unit
) : ListAdapter<RequirementCard, RequirementCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: RequirementCardViewHolder, position: Int) {
        holder.bind(currentList[position], cardClickListener, leftButtonClick, rightButtonClick)
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].status?.asInt!!
}