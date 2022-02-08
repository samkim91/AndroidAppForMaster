package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel

// 진행중탭의 viewHolders

// 시공예정 상태
class RepairingCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, mainViewModel, viewModel, binding) {
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
    mainViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, mainViewModel, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setRequestReviewButton(requirementCard)
    }
}