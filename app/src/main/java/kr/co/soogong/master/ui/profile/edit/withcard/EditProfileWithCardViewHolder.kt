package kr.co.soogong.master.ui.profile.edit.withcard.portfolio

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.profile.Portfolio
import kr.co.soogong.master.data.model.profile.PriceByProject
import kr.co.soogong.master.databinding.ViewHolderPortfolioBinding
import kr.co.soogong.master.databinding.ViewHolderPriceByProjectsBinding

abstract class EditProfileWithCardViewHolder(
    open val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    open fun binding(
        portfolio: Portfolio,
        leftButtonClickListener: (id: Int) -> Unit,
        rightButtonClickListener: (id: Int) -> Unit,
    ) { }

    open fun binding(
        priceByProject: PriceByProject,
        leftButtonClickListener: ((id: Int) -> Unit),
        rightButtonClickListener: ((id: Int) -> Unit),
    ) { }
}

class PortfolioViewHolder(
    override val binding: ViewHolderPortfolioBinding,
) : EditProfileWithCardViewHolder(binding) {

    override fun binding(
        portfolio: Portfolio,
        leftButtonClickListener: ((id: Int) -> Unit),
        rightButtonClickListener: ((id: Int) -> Unit),
    ) {
        with(binding) {
            data = portfolio

            setDeleteButtonClickListener {
                leftButtonClickListener(portfolio.id)
            }

            setEditButtonClickListener {
                rightButtonClickListener(portfolio.id)
            }

            executePendingBindings()
        }
    }
}

class PriceByProjectsViewHolder(
    override val binding: ViewHolderPriceByProjectsBinding,
) : EditProfileWithCardViewHolder(binding) {

    override fun binding(
        priceByProject: PriceByProject,
        leftButtonClickListener: ((id: Int) -> Unit),
        rightButtonClickListener: ((id: Int) -> Unit),
    ) {
        with(binding) {
            data = priceByProject

            setDeleteButtonClickListener {
                leftButtonClickListener(priceByProject.id)
            }

            setEditButtonClickListener {
                rightButtonClickListener(priceByProject.id)
            }

            executePendingBindings()
        }
    }
}
