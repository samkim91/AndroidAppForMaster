package kr.co.soogong.master.ui.requirements.received

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.databinding.ViewholderReceivedItemBinding

class ReceivedAdapter : ListAdapter<ReceivedCard, ReceivedViewHolder>(ReceivedCardDiffUtil()) {

    lateinit var buttonClick: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceivedViewHolder {
        val binding = ViewholderReceivedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReceivedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceivedViewHolder, position: Int) {
        holder.binding.run {
            val item = currentList[position]
            vm = item

            setDetailBtnListener {
                buttonClick(item.keycode)
            }

            executePendingBindings()
        }
    }
}