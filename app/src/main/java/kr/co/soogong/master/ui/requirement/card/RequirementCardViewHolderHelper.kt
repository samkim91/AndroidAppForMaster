package kr.co.soogong.master.ui.requirement.card

import android.view.LayoutInflater
import android.view.ViewGroup
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding

object RequirementCardViewHolderHelper {
    fun create(parent: ViewGroup, viewType: Int): EstimationCardViewHolder {
        val binding = ViewHolderRequirementItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return when (viewType) {
            RequirementStatus.Requested.toInt() -> {
                RequestedViewHolder(binding)
            }
            RequirementStatus.Estimated.toInt() -> {
                EstimatedViewHolder(binding)
            }
            RequirementStatus.Repairing.toInt() -> {
                RepairingViewHolder(binding)
            }
            RequirementStatus.RequestFinish.toInt() -> {
                RequestFinishViewHolder(binding)
            }
            RequirementStatus.Done.toInt() -> {
                DoneViewHolder(binding)
            }
            RequirementStatus.Closed.toInt() -> {
                ClosedViewHolder(binding)
            }
            else -> {
                CanceledViewHolder(binding)
            }
        }
    }
}