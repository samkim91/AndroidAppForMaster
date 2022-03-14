package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.domain.entity.profile.portfolio.IPortfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.Portfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.PriceByProject
import kr.co.soogong.master.domain.entity.profile.portfolio.RepairPhoto

class PortfolioListDiffUtil : DiffUtil.ItemCallback<IPortfolio>() {
    override fun areItemsTheSame(oldItem: IPortfolio, newItem: IPortfolio): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IPortfolio, newItem: IPortfolio): Boolean {
        return when {
            oldItem is Portfolio && newItem is Portfolio -> (oldItem as Portfolio) == (newItem as Portfolio)
            oldItem is RepairPhoto && newItem is RepairPhoto -> (oldItem as RepairPhoto) == (newItem as RepairPhoto)
            oldItem is PriceByProject && newItem is PriceByProject -> (oldItem as PriceByProject) == (newItem as PriceByProject)
            else -> false
        }
    }
}