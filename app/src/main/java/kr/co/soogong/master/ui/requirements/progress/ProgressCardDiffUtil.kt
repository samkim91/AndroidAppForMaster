package kr.co.soogong.master.ui.requirements.progress

import androidx.recyclerview.widget.DiffUtil

class ProgressCardDiffUtil : DiffUtil.ItemCallback<ProgressCard>() {
    override fun areItemsTheSame(oldItem: ProgressCard, newItem: ProgressCard): Boolean {
        return oldItem.keycode == newItem.keycode
    }

    override fun areContentsTheSame(oldItem: ProgressCard, newItem: ProgressCard): Boolean {
        return oldItem == newItem
    }
}