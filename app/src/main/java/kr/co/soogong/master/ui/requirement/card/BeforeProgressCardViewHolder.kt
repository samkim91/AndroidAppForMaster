package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.utility.extension.setEstimationDueTime
import kr.co.soogong.master.utility.extension.setRequestMeasureDueTime

// 진행전 탭의 viewHolders (견적요청, 실측, 매칭대기)

// 견적요청 상태
class RequestedCardViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setCallToClientButton(requirementCard)
        binding.tvDueTime.setEstimationDueTime(requirementCard.createdAt)
    }
}

// 실측요청 상태
class RequestMeasureCardViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
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
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setSendMeasureButton(requirementCard)
    }
}

// 실측완료 상태
class MeasuredCardViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setCallToClientButton(requirementCard)
    }
}

// 상담요청 상태
class RequestConsultCardViewHolder(
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

// 매칭대기 상태
class EstimatedCardViewHolder(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val viewModel: RequirementViewModel,
    private val binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding) {
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        setCallToClientButton(requirementCard)
    }
}