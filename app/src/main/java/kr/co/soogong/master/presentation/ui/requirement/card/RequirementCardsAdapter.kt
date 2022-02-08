package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel

class RequirementCardsAdapter(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val mainViewModel: MainViewModel,
    private val viewModel: RequirementsViewModel,
) : ListAdapter<RequirementCard, RequirementCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RequirementCardViewHolder.create(context,
            fragmentManager,
            mainViewModel,
            viewModel,
            parent,
            viewType)

    override fun onBindViewHolder(holder: RequirementCardViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemViewType(position: Int): Int =
        currentList[position].status.asInt
}