package kr.co.soogong.master.ui.requirements.received

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewholderReceivedItemBinding

class ReceivedAdapter : ListAdapter<ReceivedCard, ReceivedViewHolder>(ReceivedCardDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceivedViewHolder {
        val binding = DataBindingUtil.inflate<ViewholderReceivedItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_received_item,
            parent,
            false
        )
        return ReceivedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceivedViewHolder, position: Int) {
        holder.binding.run {
            vm = currentList[position]
            executePendingBindings()
        }
    }
}