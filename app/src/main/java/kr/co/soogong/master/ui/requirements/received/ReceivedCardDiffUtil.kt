package kr.co.soogong.master.ui.requirements.received

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.requirements.Requirement

class ReceivedCardDiffUtil: DiffUtil.ItemCallback<ReceivedCard>() {
    override fun areItemsTheSame(oldItem: ReceivedCard, newItem: ReceivedCard): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReceivedCard, newItem: ReceivedCard): Boolean {
        return oldItem == newItem
    }
}