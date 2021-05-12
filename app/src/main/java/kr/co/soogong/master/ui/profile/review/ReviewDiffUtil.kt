package kr.co.soogong.master.ui.profile.review

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.profile.Review

class ReviewDiffUtil : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}