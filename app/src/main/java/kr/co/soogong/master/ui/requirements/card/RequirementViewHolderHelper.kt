package kr.co.soogong.master.ui.requirements.card

import android.view.LayoutInflater
import android.view.ViewGroup
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding

fun create(parent: ViewGroup, requirementCard: RequirementCard): RequirementViewHolder {
    val binding = ViewHolderRequirementItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )

    return when (requirementCard.status) {
        RequirementStatus.Request -> {
            RequestViewHolder(binding)
        }
        RequirementStatus.Waiting -> {
            WaitingViewHolder(binding)
        }
        RequirementStatus.Progress -> {
            ProgressViewHolder(binding)
        }
        RequirementStatus.CustomDone -> {
            CustomDoneViewHolder(binding)
        }
        RequirementStatus.Done -> {
            DoneViewHolder(binding)
        }
        RequirementStatus.Final -> {
            FinalViewHolder(binding)
        }
        RequirementStatus.Cancel -> {
            CancelViewHolder(binding)
        }
    }
}