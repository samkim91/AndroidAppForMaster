package kr.co.soogong.master.presentation.ui.profile.review

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.domain.entity.profile.Review

@BindingAdapter("bind:reviews")
fun RecyclerView.setList(items: List<Review>?) {
    (adapter as? ReviewAdapter)?.submitList(items?.toMutableList() ?: emptyList())
}