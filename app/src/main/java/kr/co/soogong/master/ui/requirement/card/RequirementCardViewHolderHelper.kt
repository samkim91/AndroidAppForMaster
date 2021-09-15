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
                RequestedViewHolder(parent.context, binding)
            }
            Estimated.asInt -> {
                EstimatedViewHolder(parent.context, binding)
            }
            RequestConsult.asInt -> {
                RequestConsultViewHolder(parent.context, binding)
            }
            Measuring.asInt -> {
                MeasuringViewHolder(parent.context, binding)
            }
            Measured.asInt -> {
                MeasuredViewHolder(parent.context, binding)
            }
            Repairing.asInt -> {
                RepairingViewHolder(parent.context, binding)
            }
            RequestFinish.asInt -> {
                RequestFinishViewHolder(parent.context, binding)
            }
            Done.asInt -> {
                DoneViewHolder(parent.context, binding)
            }
            Closed.asInt -> {
                ClosedViewHolder(parent.context, binding)
            }
            else -> {
                CanceledViewHolder(parent.context, binding)
            }
        }
    }
}