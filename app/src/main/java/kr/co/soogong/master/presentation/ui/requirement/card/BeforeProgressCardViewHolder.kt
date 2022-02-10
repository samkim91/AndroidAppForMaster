package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.domain.entity.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel
import kr.co.soogong.master.utility.extension.*

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
    override fun bind(
        requirementCard: RequirementCard,
    ) {
        super.bind(requirementCard)

        binding.buttonRight.setAcceptingEstimation(fragmentManager,
            mainViewModel,
            requirementCard.id)
    }
}

// 매칭대기 상태
// 버튼 : 1. 전화 하기
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
            setEstimationPrice(requirementCard.estimationPrice)

            buttonLeft.setCallToClient(fragmentManager,
                mainViewModel,
                viewModel,
                requirementCard.phoneNumber,
                requirementCard.estimationId,
                requirementCard.isCalled)
        }
    }
}

// 상담요청 상태
// 버튼 : 1. 전화 하기 / 2. (견적요청일 경우) 견적 보내기
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

            buttonLeft.setCallToClient(fragmentManager,
                mainViewModel,
                viewModel,
                requirementCard.phoneNumber,
                requirementCard.estimationId,
                requirementCard.isCalled)

            // NOTE: 2022/02/09 subStatus 가 Requested 또는 Estimated 를 가짐. Requested 이면 견적보내기 버튼, Estimated 이면 나의 견적가 전시
            if (requirementCard.subStatus == RequirementStatus.Requested.code) {
                buttonRight.setAcceptingEstimation(fragmentManager,
                    mainViewModel,
                    requirementCard.id)
            } else {
                setEstimationPrice(requirementCard.estimationPrice)
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

        binding.buttonRight.setAcceptingMeasure(
            fragmentManager,
            mainViewModel,
            viewModel,
            EstimationDto(
                id = requirementCard.estimationId,
                requirementId = requirementCard.id,
                masterResponseCode = EstimationResponseCode.ACCEPTED,
                masterId = null,
                token = null,
                typeCode = null,
                price = null,
            )
        )
    }
}

// 실측예정 상태
// 버튼 : 1. 전화 하기 / 2. 견적 보내기
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
            buttonLeft.setCallToClient(fragmentManager,
                mainViewModel,
                viewModel,
                requirementCard.phoneNumber,
                requirementCard.estimationId,
                requirementCard.isCalled)

            buttonRight.setSendMeasure(fragmentManager, mainViewModel, requirementCard.id)
        }
    }
}

// 실측완료 상태
// 버튼 : 1. 전화 하기 / 2. 시공 완료
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
            setEstimationPrice(requirementCard.estimationPrice)

            buttonLeft.setCallToClient(fragmentManager,
                mainViewModel,
                viewModel,
                requirementCard.phoneNumber,
                requirementCard.estimationId,
                requirementCard.isCalled)

            buttonRight.setRepairDone(fragmentManager, mainViewModel, requirementCard.id)
        }
    }
}
