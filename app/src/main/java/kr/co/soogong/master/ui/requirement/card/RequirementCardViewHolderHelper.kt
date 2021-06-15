package kr.co.soogong.master.ui.requirement.card

import android.view.LayoutInflater
import android.view.ViewGroup
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.data.model.requirement.EstimationStatus

object RequirementCardViewHolderHelper {
    fun create(parent: ViewGroup, viewType: Int): EstimationCardViewHolder {
        val binding = ViewHolderRequirementItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return when (viewType) {
            EstimationStatus.Request.toInt() -> {
                RequestViewHolder(binding)
            }
            EstimationStatus.Waiting.toInt() -> {
                WaitingViewHolder(binding)
            }
            EstimationStatus.Progress.toInt() -> {
                ProgressViewHolder(binding)
            }
            EstimationStatus.CustomDone.toInt() -> {
                CustomDoneViewHolder(binding)
            }
            EstimationStatus.Done.toInt() -> {
                DoneViewHolder(binding)
            }
            EstimationStatus.Final.toInt() -> {
                FinalViewHolder(binding)
            }
            else -> {
                CancelViewHolder(binding)
            }
        }
    }
}