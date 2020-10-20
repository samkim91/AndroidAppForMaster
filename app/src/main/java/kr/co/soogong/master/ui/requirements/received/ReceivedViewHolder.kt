package kr.co.soogong.master.ui.requirements.received

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewholderReceivedItemBinding

class ReceivedViewHolder(
    val binding: ViewholderReceivedItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        receivedCard: ReceivedCard,
        buttonClick: (String) -> Unit
    ) {
        binding.run {
            vm = receivedCard

            setDetailBtnListener {
                buttonClick(receivedCard.keycode)
            }

            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): ReceivedViewHolder {
            val binding = ViewholderReceivedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ReceivedViewHolder(binding)
        }
    }
}