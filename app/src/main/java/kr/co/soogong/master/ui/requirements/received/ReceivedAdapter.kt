package kr.co.soogong.master.ui.requirements.received

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.requirements.RequirementCardDiffUtil
import kr.co.soogong.master.util.extension.dp
import kr.co.soogong.master.ui.requirements.card.EstimationCardViewHolder
import kr.co.soogong.master.ui.requirements.card.RequirementCardViewHolderHelper

class ReceivedAdapter(
    private val cardClickListener: (String) -> Unit
) : ListAdapter<RequirementCard, EstimationCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: EstimationCardViewHolder, position: Int) {
        if (position == 0) {
            val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = 72.dp
            holder.itemView.layoutParams = params
        }
        holder.binding(getItem(position), cardClickListener, null, null)
    }

    override fun getItemViewType(position: Int) = currentList[position].status.toInt()
}