package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.main.MainViewModel
import kr.co.soogong.master.ui.requirement.list.RequirementsViewModel
import kr.co.soogong.master.utility.extension.setRequestMeasureDueTime

// 진행전 탭의 viewHolders (견적요청, 실측, 매칭대기)

// 견적요청 상태
class RequestedCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    activityViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, activityViewModel, viewModel, binding)

// 실측요청 상태
class RequestMeasureCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    activityViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, activityViewModel, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setCallToClientButton(requirementCard)
        binding.tvDueTime.setRequestMeasureDueTime(requirementCard.createdAt)
    }
}

// 실측예정 상태
class MeasuringCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    activityViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, activityViewModel, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setSendMeasureButton(requirementCard)
    }
}

// 실측완료 상태
class MeasuredCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    activityViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, activityViewModel, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setRepairDoneButton(requirementCard)
    }
}

// 상담요청 상태
class RequestConsultCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    activityViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, activityViewModel, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setCallToClientButton(requirementCard)
    }
}

// 매칭대기 상태
class EstimatedCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    activityViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, activityViewModel, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setCallToClientButton(requirementCard)
    }
}