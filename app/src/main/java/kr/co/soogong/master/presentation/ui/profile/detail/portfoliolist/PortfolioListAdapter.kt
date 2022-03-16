package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.domain.entity.common.PortfolioType
import kr.co.soogong.master.domain.entity.profile.portfolio.IPortfolio

class PortfolioListAdapter(
    private val context: Context,
    private val buttonLeftClickListener: (iPortfolio: IPortfolio) -> Unit,
    private val buttonRightClickListener: (iPortfolio: IPortfolio) -> Unit,
) : ListAdapter<IPortfolio, PortfolioCommonViewHolder>(PortfolioListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PortfolioCommonViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: PortfolioCommonViewHolder, position: Int) =
        holder.bind(context,
            currentList[position],
            buttonLeftClickListener,
            buttonRightClickListener)

    override fun getItemViewType(position: Int): Int =
        when (currentList[position].type) {
            PortfolioType.PORTFOLIO -> 0
            PortfolioType.REPAIR_PHOTO -> 1
            PortfolioType.PRICE_BY_PROJECT -> 2
        }
}