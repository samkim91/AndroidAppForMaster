package kr.co.soogong.master.ui.profile.review

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.profile.Review
import kr.co.soogong.master.databinding.ViewHolderReviewItemBinding
import kr.co.soogong.master.utility.extension.dp

class ReviewAdapter (
    private val context: Context,
) : ListAdapter<Review, ReviewViewHolder>(ReviewDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewViewHolderHelper.create(parent)

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int){
        holder.itemView.layoutParams.apply {
            this as ViewGroup.MarginLayoutParams
            this.setMargins(20.dp)
        }

        holder.binding(context ,currentList[position])
    }
}