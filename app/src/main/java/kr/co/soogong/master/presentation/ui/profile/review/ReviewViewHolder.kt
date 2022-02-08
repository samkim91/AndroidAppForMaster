package kr.co.soogong.master.presentation.ui.profile.review

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.domain.entity.profile.Review
import kr.co.soogong.master.databinding.ViewHolderReviewItemBinding
import kr.co.soogong.master.presentation.ui.common.image.RectangleImageAdapter
import kr.co.soogong.master.presentation.uihelper.common.image.ImageViewActivityHelper

class ReviewViewHolder(
    val binding: ViewHolderReviewItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun binding(
        context: Context,
        review: Review,
    ) {
        with(binding) {
            data = review

            reviewPhotoList.adapter = RectangleImageAdapter(
                cardClickListener = { position ->
                    context.startActivity(
                        ImageViewActivityHelper.getIntent(
                            context,
                            review.images,
                            position
                        )
                    )
                }
            )

            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): ReviewViewHolder = ReviewViewHolder(
            ViewHolderReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}