package kr.co.soogong.master.ui.requirement.progress

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolder
import kr.co.soogong.master.ui.requirement.card.RequirementCardDiffUtil
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolderHelper

class ProgressAdapter(
    private val cardClickListener: (Int) -> Unit,
    private val callButtonClick: (Int, Any?) -> Unit,
    private val doneButtonClick: (Int, Any?) -> Unit
) : ListAdapter<RequirementCard, RequirementCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: RequirementCardViewHolder, position: Int) {
        holder.bind(getItem(position), cardClickListener, callButtonClick, doneButtonClick)
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].status?.asInt!!
}