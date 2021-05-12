package kr.co.soogong.master.ui.profile.review

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.profile.Review
import kr.co.soogong.master.databinding.ViewHolderReviewItemBinding
import kr.co.soogong.master.ui.image.RectangleImageAdapter
import kr.co.soogong.master.uiinterface.image.ImageViewActivityHelper

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
                            review.imageList,
                            position
                        )
                    )
                }
            )

            executePendingBindings()
        }
    }
}