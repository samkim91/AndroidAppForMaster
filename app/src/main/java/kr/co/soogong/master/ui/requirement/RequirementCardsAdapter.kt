package kr.co.soogong.master.ui.requirement

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolder
import kr.co.soogong.master.ui.requirement.card.RequirementCardDiffUtil
import kr.co.soogong.master.ui.requirement.card.RequirementCardViewHolderHelper

class RequirementCardsAdapter(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: BaseViewModel,
) : ListAdapter<RequirementCard, RequirementCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: RequirementCardViewHolder, position: Int) {
        holder.bind(context, fragmentManager, viewModel, currentList[position])
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].status?.asInt!!
}