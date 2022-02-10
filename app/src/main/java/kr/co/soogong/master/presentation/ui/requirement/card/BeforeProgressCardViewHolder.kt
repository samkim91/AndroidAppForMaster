package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel

// 진행전 탭의 viewHolders (견적요청, 실측, 매칭대기)

// 견적요청 상태
// 버튼 : 2. 견적 보내기
class RequestedCardViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    mainViewModel: MainViewModel,
    viewModel: RequirementsViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, mainViewModel, viewModel, binding) {
    override fun bind(requirementCard: RequirementCard) {
        super.bind(requirementCard)

        with(binding) {
            buttonRight.setSendingEstimation(requirementCard)
        }
    }
}

// 매칭대기 상태
// 버튼 : 1. 전화 하기
// 기타 : 견적가
class EstimatedCardViewHolder(
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

        with(binding) {
            buttonRight.setSendingEstimation(requirementCard)
            buttonLeft.setCallToClient(requirementCard)
        }
    }
}

// 상담요청 상태
// 버튼 : 1. 전화 하기(기본), 2. 견적 보내기(견적요청 상태일 때)
// 기타 : 견적가(매칭 대기상태일 때)
class RequestConsultCardViewHolder(
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

        with(binding) {
            buttonLeft.setCallToClient(requirementCard)

            if (requirementCard.subStatus == RequirementStatus.Requested.code) {
                buttonRight.setSendingEstimation(requirementCard)
            } else {
                setEstimationPrice(requirementCard)
            }
        }
    }
}

// 실측요청 상태
// 버튼 : 2. 실측 수락
class RequestMeasureCardViewHolder(
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

        with(binding) {
            buttonRight.setAcceptingMeasure(requirementCard)
        }
    }
}

// 실측예정 상태
// 버튼 : 1. 전화 하기, 2. 견적 보내기
class MeasuringCardViewHolder(
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

        with(binding) {
            buttonLeft.setCallToClient(requirementCard)
            buttonRight.setSendingEstimation(requirementCard)
        }
    }
}

// 실측완료 상태
// 버튼 : 1. 전화 하기
// 기타 : 견적가
class MeasuredCardViewHolder(
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

        with(binding) {
            buttonLeft.setCallToClient(requirementCard)
            setEstimationPrice(requirementCard)
        }
    }
}