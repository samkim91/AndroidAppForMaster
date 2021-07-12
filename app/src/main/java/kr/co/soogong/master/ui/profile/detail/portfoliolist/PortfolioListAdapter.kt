package kr.co.soogong.master.ui.profile.detail.portfoliolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.databinding.ViewHolderPortfolioBinding

class PortfolioListAdapter(
    private val leftButtonClickListener: (id: Int) -> Unit,
    private val rightButtonClickListener: (id: Int) -> Unit
) : ListAdapter<PortfolioDto, PortfolioViewHolder>(PortfolioListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PortfolioViewHolder(
            ViewHolderPortfolioBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.binding(currentList[position], leftButtonClickListener, rightButtonClickListener)
    }
}