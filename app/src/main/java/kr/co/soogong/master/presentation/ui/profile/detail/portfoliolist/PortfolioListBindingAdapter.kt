package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.domain.entity.profile.portfolio.IPortfolio

@BindingAdapter("portfolios")
fun RecyclerView.setPortfolios(items: List<IPortfolio>?) {
    (adapter as? PortfolioListAdapter)?.submitList(items?.toMutableList())
}