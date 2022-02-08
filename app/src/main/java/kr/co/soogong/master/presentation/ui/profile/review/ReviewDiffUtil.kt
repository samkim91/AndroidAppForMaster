package kr.co.soogong.master.presentation.ui.profile.review

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.domain.entity.profile.Review

class ReviewDiffUtil : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}