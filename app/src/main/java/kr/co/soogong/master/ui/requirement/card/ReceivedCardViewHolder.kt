package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.utility.extension.setEstimationDueTime
import kr.co.soogong.master.utility.extension.setRequestMeasureDueTime

// 문의탭의 viewHolders

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

        with(binding) {
            setApprovedMasterOnly(requirementCard)
            setRequirementCardStatusTheme(THEME_BLUE)
            textViewDueTime.setEstimationDueTime(requirementCard.createdAt)
        }
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

        with(binding) {
            setApprovedMasterOnly(requirementCard)
            setRequirementCardStatusTheme(THEME_BLUE)
            textViewDueTime.setRequestMeasureDueTime(requirementCard.createdAt)
        }
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

        with(binding) {
            setApprovedMasterOnly(requirementCard)
            setRequirementCardStatusTheme(THEME_BLUE)
        }
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

        with(binding) {
            setApprovedMasterOnly(requirementCard)
            setRequirementCardStatusTheme(THEME_GREY)

//            if (!requirementCard.safetyNumber.isNullOrEmpty()) setCallToClientButton(requirementCard)
//            setRepairDoneButtonConfirming(requirementCard)

        }
    }
}