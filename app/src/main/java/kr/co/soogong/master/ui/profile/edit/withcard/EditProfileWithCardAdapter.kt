package kr.co.soogong.master.ui.profile.edit.withcard

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.profile.IEditProfileWithCard
import kr.co.soogong.master.data.model.profile.Portfolio
import kr.co.soogong.master.data.model.profile.PriceByProject
import kr.co.soogong.master.ui.profile.edit.withcard.portfolio.EditProfileWithCardViewHolder
import kr.co.soogong.master.ui.profile.edit.withcard.portfolio.PortfolioViewHolder
import kr.co.soogong.master.ui.profile.edit.withcard.EditProfileWithCardViewHolderHelper.PORTFOLIO_VIEW_TYPE
import kr.co.soogong.master.ui.profile.edit.withcard.EditProfileWithCardViewHolderHelper.PRICE_BY_PROJECTS_VIEW_TYPE

class EditProfileWithCardAdapter(
    private val leftButtonClickListener: (id: Int) -> Unit,
    private val rightButtonClickListener: (id: Int) -> Unit
) : ListAdapter<IEditProfileWithCard, EditProfileWithCardViewHolder>(EditProfileWithCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EditProfileWithCardViewHolderHelper.create(parent, viewType)

    override fun onBindViewHolder(holder: EditProfileWithCardViewHolder, position: Int) {
        if (holder is PortfolioViewHolder) {
            holder.binding(
                currentList[position] as Portfolio,
                leftButtonClickListener,
                rightButtonClickListener
            )
        } else {
            holder.binding(
                currentList[position] as PriceByProject,
                leftButtonClickListener,
                rightButtonClickListener
            )
        }
    }

    override fun getItemViewType(position: Int) = when (currentList[position]) {
        is Portfolio -> PORTFOLIO_VIEW_TYPE
        else -> PRICE_BY_PROJECTS_VIEW_TYPE
    }
}