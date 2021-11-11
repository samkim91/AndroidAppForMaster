package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel

// 진행중탭의 viewHolders

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

//        setCallToClientButton(requirementCard)
//        setWriteEstimationButton(requirementCard)
        setRequirementCardStatusTheme(THEME_BLUE)

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

        with(binding) {

//            setCallToClientButton(requirementCard)
//            setRepairDoneButton(requirementCard)
            setRequirementCardStatusTheme(THEME_GREY)

        }
    }
}

// 시공예정 상태
class RepairingCardViewHolder(
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
//            setCallToClientButton(requirementCard)
//            setRepairDoneButton(requirementCard)
            setRequirementCardStatusTheme(THEME_BLUE)

        }
    }
}