package kr.co.soogong.master.ui.requirement.card

import android.view.LayoutInflater
import android.view.ViewGroup
import kr.co.soogong.master.data.model.requirement.*
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding

object RequirementCardViewHolderHelper {
    fun create(parent: ViewGroup, viewType: Int): RequirementCardViewHolder {
        val binding = ViewHolderRequirementItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return when (viewType) {
            RequirementStatus.Requested.asInt -> {
                RequestedCardViewHolder(binding)
            }
            RequirementStatus.Estimated.asInt -> {
                EstimatedCardViewHolder(binding)
            }
            RequirementStatus.RequestConsult.asInt -> {
                RequestConsultCardViewHolder(binding)
            }
            RequirementStatus.Measuring.asInt -> {
                MeasuringCardViewHolder(binding)
            }
            RequirementStatus.Measured.asInt -> {
                MeasuredCardViewHolder(binding)
            }
            RequirementStatus.Repairing.asInt -> {
                RepairingCardViewHolder(binding)
            }
            RequirementStatus.RequestFinish.asInt -> {
                RequestFinishCardViewHolder(binding)
            }
            RequirementStatus.Done.asInt -> {
                DoneViewHolder(binding)
            }
            RequirementStatus.Closed.asInt -> {
                ClosedViewHolder(binding)
            }
            else -> {
                CanceledViewHolder(binding)
            }
        }
    }
}