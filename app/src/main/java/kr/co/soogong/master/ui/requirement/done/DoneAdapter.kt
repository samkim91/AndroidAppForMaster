package kr.co.soogong.master.ui.requirement.done

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.requirement.card.RequirementCardDiffUtil
import kr.co.soogong.master.ui.requirement.card.EstimationCardViewHolder
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolderHelper
import kr.co.soogong.master.utility.extension.dp

class DoneAdapter(
    private val cardClickListener: (requirementId: Int) -> Unit,
    private val reviewButtonClick: (requirementId: Int) -> Unit
) : ListAdapter<RequirementCard, EstimationCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: EstimationCardViewHolder, position: Int) {
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = if (position == 0) 72.dp else 0.dp
        holder.itemView.layoutParams = params
        holder.binding(getItem(position), cardClickListener, null, reviewButtonClick)
    }

    override fun getItemViewType(position: Int) = currentList[position].status.toInt()
}