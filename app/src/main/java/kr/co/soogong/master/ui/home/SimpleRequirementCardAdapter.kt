package kr.co.soogong.master.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.requirement.RequirementCardV2
import kr.co.soogong.master.databinding.ViewHolderSimpleRequirementItemBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.card.RequirementCardDiffUtil
import kr.co.soogong.master.utility.extension.dp

class SimpleRequirementCardAdapter(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
) : ListAdapter<RequirementCardV2, SimpleRequirementCardViewHolder>(RequirementCardDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SimpleRequirementCardViewHolder =
        SimpleRequirementCardViewHolder(
            ViewHolderSimpleRequirementItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SimpleRequirementCardViewHolder, position: Int) {
        holder.itemView.layoutParams =
            (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                bottomMargin = 15.dp
                topMargin = 15.dp
            }

        holder.bind(context, fragmentManager, viewModel, currentList[position], position)
    }
}