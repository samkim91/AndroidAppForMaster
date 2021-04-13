package kr.co.soogong.master.ui.requirements.done

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.requirements.RequirementCardDiffUtil
import kr.co.soogong.master.ui.requirements.card.EstimationCardViewHolder
import kr.co.soogong.master.ui.requirements.card.RequirementCardViewHolderHelper
import kr.co.soogong.master.util.extension.dp

class DoneAdapter(
    private val cardClickListener: (String, EstimationStatus) -> Unit,
    private val reviewButtonClick: (String) -> Unit
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