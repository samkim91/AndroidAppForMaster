package kr.co.soogong.master.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderSimpleRequirementItemBinding
import kr.co.soogong.master.ui.main.MainViewModel
import kr.co.soogong.master.ui.requirement.card.RequirementCardDiffUtil

class SimpleRequirementCardAdapter(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val mainViewModel: MainViewModel,
) : ListAdapter<RequirementCard, SimpleRequirementCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SimpleRequirementCardViewHolder(
            ViewHolderSimpleRequirementItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SimpleRequirementCardViewHolder, position: Int) =
        holder.bind(context, fragmentManager, mainViewModel, currentList[position], position)
}