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
            Requested.asInt -> {
                RequestedViewHolder(binding)
            }
            Estimated.asInt -> {
                EstimatedViewHolder(binding)
            }
            Repairing.asInt -> {
                RepairingViewHolder(binding)
            }
            RequestFinish.asInt -> {
                RequestFinishViewHolder(binding)
            }
            Done.asInt -> {
                DoneViewHolder(binding)
            }
            Closed.asInt -> {
                ClosedViewHolder(binding)
            }
            else -> {
                CanceledViewHolder(binding)
            }
        }
    }
}