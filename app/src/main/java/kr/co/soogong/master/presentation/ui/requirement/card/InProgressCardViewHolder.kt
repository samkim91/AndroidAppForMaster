package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel
import kr.co.soogong.master.utility.extension.setAskForReview
import kr.co.soogong.master.utility.extension.setCallToClient
import kr.co.soogong.master.utility.extension.setRepairDone

// 진행중탭의 viewHolders

// 시공예정 상태
// 버튼 : 1. 전화 하기, 2. 시공 완료
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

        with(binding) {
            setEstimationPrice(requirementCard.estimationPrice)

            buttonLeft.setCallToClient(fragmentManager,
                mainViewModel,
                viewModel,
                requirementCard.phoneNumber,
                requirementCard.estimationId,
                requirementCard.isCalled
            )

            buttonRight.setRepairDone(fragmentManager, mainViewModel, requirementCard.id)
        }
    }
}

// 시공완료 상태
// 버튼 : 2. 리뷰 요청
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

        with(binding) {
            setRepairPrice(requirementCard.repairPrice)

            buttonRight.setAskForReview(
                fragmentManager,
                mainViewModel,
                viewModel,
                RepairDto(
                    id = requirementCard.repairId,
                    requirementToken = requirementCard.token,
                    estimationId = requirementCard.estimationId
                ),
                requirementCard.requestReviewYn
            )
        }
    }
}