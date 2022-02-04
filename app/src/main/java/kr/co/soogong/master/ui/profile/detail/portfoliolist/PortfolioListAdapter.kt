package kr.co.soogong.master.ui.profile.detail.portfoliolist

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.global.CodeTable

class PortfolioListAdapter(
    private val context: Context,
    private val buttonLeftClickListener: (id: Int) -> Unit,
    private val buttonRightClickListener: (portfolioDto: PortfolioDto) -> Unit,
) : ListAdapter<PortfolioDto, PortfolioCommonViewHolder>(PortfolioListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PortfolioCommonViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: PortfolioCommonViewHolder, position: Int) =
        holder.binding(context,
            currentList[position],
            buttonLeftClickListener,
            buttonRightClickListener)

    override fun getItemViewType(position: Int): Int =
        when (currentList[position].type) {
            CodeTable.PORTFOLIO.code -> 0
            else -> 1
        }
}