package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.model.requirement.RequirementCardV2
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel

// 진행중탭의 viewHolders

// 시공예정 상태
class RepairingCardViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCardV2,
    ) {
        super.bind(requirementCard)

        setRepairDoneButton(requirementCard)
    }
}

// 시공완료 상태
class DoneViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCardV2,
    ) {
        super.bind(requirementCard)

        setRequestReviewButton(requirementCard)
    }
}