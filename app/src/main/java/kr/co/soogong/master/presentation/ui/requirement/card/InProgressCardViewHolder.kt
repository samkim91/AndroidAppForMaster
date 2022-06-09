package kr.co.soogong.master.presentation.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel

// 시공예정 상태
// 버튼 : 1. 전화 하기, 2. 시공 완료
// 기타 : 견적가
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
            setEstimationPrice(requirementCard)
            buttonLeft.setCallToClient(requirementCard)
            buttonRight.setRepairDone(requirementCard)
        }
    }
}