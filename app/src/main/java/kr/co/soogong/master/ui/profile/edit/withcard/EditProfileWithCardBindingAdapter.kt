package kr.co.soogong.master.ui.profile.edit.withcard

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.profile.IPortfolio

@BindingAdapter("bind:item_list")
fun RecyclerView.setList(items: List<IPortfolio>?) {
    (adapter as? EditProfileWithCardAdapter)?.submitList(items ?: emptyList())
}