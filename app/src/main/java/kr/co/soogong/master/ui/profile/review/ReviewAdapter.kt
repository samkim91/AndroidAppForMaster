package kr.co.soogong.master.ui.profile.review

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.profile.Review

class ReviewAdapter(
    private val context: Context,
) : ListAdapter<Review, ReviewViewHolder>(ReviewDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewViewHolderHelper.create(parent)

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) =
        holder.binding(context, currentList[position])
}