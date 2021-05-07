package kr.co.soogong.master.ui.profile.edit.withcard

import android.view.LayoutInflater
import android.view.ViewGroup
import kr.co.soogong.master.databinding.ViewHolderPortfolioBinding
import kr.co.soogong.master.databinding.ViewHolderPriceByProjectsBinding
import kr.co.soogong.master.ui.profile.edit.portfolio.EditProfileWithCardViewHolder
import kr.co.soogong.master.ui.profile.edit.portfolio.PortfolioViewHolder
import kr.co.soogong.master.ui.profile.edit.portfolio.PriceByProjectsViewHolder

object EditProfileWithCardViewHolderHelper {
    const val PORTFOLIO_VIEW_TYPE = 0
    const val PRICE_BY_PROJECTS_VIEW_TYPE = 1

    fun create(parent: ViewGroup, viewType: Int): EditProfileWithCardViewHolder {
        return when (viewType) {
            PORTFOLIO_VIEW_TYPE -> {
                PortfolioViewHolder(ViewHolderPortfolioBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            }
            else -> {
                PriceByProjectsViewHolder(ViewHolderPriceByProjectsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            }
        }
    }
}