package kr.co.soogong.master.ui.requirements.received

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.ui.requirements.card.RequirementCard
import kr.co.soogong.master.ui.requirements.card.RequirementCardDiffUtil
import kr.co.soogong.master.ui.requirements.card.EstimationCardViewHolder
import kr.co.soogong.master.ui.requirements.card.RequirementCardViewHolderHelper

class ReceivedAdapter(
    private val buttonClick: (String) -> Unit
) : ListAdapter<RequirementCard, EstimationCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: EstimationCardViewHolder, position: Int) {
        holder.binding(getItem(position), null, null)
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].status.toInt()
    }
}