package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel
import kr.co.soogong.master.utility.extension.formatMoney

// 완료탭의 viewHolders

// 평가완료 상태
class ClosedViewHolder(
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

        binding.tvActualPrice.isVisible = true
        binding.tvActualPrice.text =
            context.getString(R.string.repair_actual_price_in_requirement_card,
                requirementCard.repairPrice.formatMoney())
    }
}

// 시공취소 상태
class CanceledViewHolder(
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

        binding.tvActualPrice.isVisible = true
        binding.tvActualPrice.text =
            context.getString(R.string.repair_actual_price_in_requirement_card,
                requirementCard.repairPrice.formatMoney())
    }
}

