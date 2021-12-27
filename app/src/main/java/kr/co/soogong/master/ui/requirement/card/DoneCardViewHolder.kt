package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel

// 완료탭의 viewHolders

// 평가완료 상태
class ClosedViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

    }
}

// 시공취소 상태
class CanceledViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

    }
}

