package kr.co.soogong.master.ui.profile.review

import android.view.LayoutInflater
import android.view.ViewGroup
import kr.co.soogong.master.databinding.ViewHolderReviewItemBinding

object ReviewViewHolderHelper {
    fun create(parent: ViewGroup): ReviewViewHolder = ReviewViewHolder(
        ViewHolderReviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}