package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel

// 진행중탭의 viewHolders

// 시공예정 상태
class RepairingCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    viewModel: RequirementViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setRepairDoneButton(requirementCard)
    }
}

// 시공완료 상태
class DoneViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    viewModel: RequirementViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setRequestReviewButton(requirementCard)
    }
}