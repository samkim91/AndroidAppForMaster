package kr.co.soogong.master.ui.requirements.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewholderProgressItemBinding

class ProgressViewHolder(
    val binding: ViewholderProgressItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        progressCard: ProgressCard,
        callButtonClick: (String) -> Unit,
        detailButtonClick: (String) -> Unit,
        removeButtonClick: (String) -> Unit
    ) {
        binding.run {
            vm = progressCard

            setCallBtnListener {
                callButtonClick(progressCard.tel)
            }

            setDetailBtnListener {
                detailButtonClick(progressCard.keycode)
            }

            setRemoveBtnListener {
                removeButtonClick(progressCard.keycode)
            }

            executePendingBindings()
        }
    }


    companion object {
        fun create(parent: ViewGroup): ProgressViewHolder {
            val binding = ViewholderProgressItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ProgressViewHolder(binding)
        }
    }
}