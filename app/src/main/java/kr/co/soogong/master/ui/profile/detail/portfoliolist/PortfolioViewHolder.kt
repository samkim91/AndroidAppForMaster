package kr.co.soogong.master.ui.profile.detail.portfoliolist.portfolio

import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.databinding.ViewHolderPortfolioBinding


class PortfolioViewHolder(
    val binding: ViewHolderPortfolioBinding,
) : RecyclerView.ViewHolder(binding.root){
    fun binding(
        portfolioDto: PortfolioDto,
        leftButtonClickListener: ((id: Int) -> Unit),
        rightButtonClickListener: ((id: Int) -> Unit),
    ) {
        with(binding) {
            data = portfolioDto

            setDeleteButtonClickListener {
                portfolioDto.id?.let {
                    leftButtonClickListener(it)
                }
            }

            setEditButtonClickListener {
                portfolioDto.id?.let {
                    rightButtonClickListener(it)
                }
            }

            executePendingBindings()
        }
    }
}