package kr.co.soogong.master.ui.profile.review

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.profile.Review

@BindingAdapter("bind:review_list")
fun RecyclerView.setList(items: List<Review>?) {
    (adapter as? ReviewAdapter)?.submitList(items ?: emptyList())
}