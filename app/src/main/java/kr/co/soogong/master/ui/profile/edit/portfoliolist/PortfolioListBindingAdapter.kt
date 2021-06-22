package kr.co.soogong.master.ui.profile.edit.portfoliolist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.profile.PortfolioDto

@BindingAdapter("bind:item_list")
fun RecyclerView.setList(items: List<PortfolioDto>?) {
    (adapter as? PortfolioListAdapter)?.submitList(items ?: emptyList())
}