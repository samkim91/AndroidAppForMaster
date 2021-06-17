package kr.co.soogong.master.ui.requirement.received

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.requirement.card.RequirementCardDiffUtil
import kr.co.soogong.master.ui.requirement.card.EstimationCardViewHolder
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolderHelper
import kr.co.soogong.master.utility.extension.dp

class ReceivedAdapter(
    private val cardClickListener: (Int) -> Unit
) : ListAdapter<RequirementCard, EstimationCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: EstimationCardViewHolder, position: Int) {
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = if (position == 0) 72.dp else 0.dp
        holder.itemView.layoutParams = params
        holder.binding(currentList[position], cardClickListener, null, null)
    }

    override fun getItemViewType(position: Int): Int =
        RequirementStatus.getStatus(currentList[position].status).toInt()
}