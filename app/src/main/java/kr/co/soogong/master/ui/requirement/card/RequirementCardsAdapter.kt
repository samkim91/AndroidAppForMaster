package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCardV2
import kr.co.soogong.master.ui.requirement.RequirementViewModel

class RequirementCardsAdapter(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
) : ListAdapter<RequirementCardV2, RequirementCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolder.create(context, fragmentManager, viewModel, parent, viewType)

    override fun onBindViewHolder(holder: RequirementCardViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemViewType(position: Int): Int =
        currentList[position].status.asInt
}