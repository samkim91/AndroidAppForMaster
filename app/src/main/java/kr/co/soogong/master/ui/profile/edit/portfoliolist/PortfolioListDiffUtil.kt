package kr.co.soogong.master.ui.profile.edit.portfoliolist

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.dto.profile.PortfolioDto

class PortfolioListDiffUtil : DiffUtil.ItemCallback<PortfolioDto>() {
    override fun areItemsTheSame(oldItem: PortfolioDto, newItem: PortfolioDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PortfolioDto, newItem: PortfolioDto): Boolean {
        return oldItem == newItem
    }
}