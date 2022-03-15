package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewHolderPortfolioBinding
import kr.co.soogong.master.databinding.ViewHolderPriceByProjectBinding
import kr.co.soogong.master.databinding.ViewHolderRepairPhotoBinding
import kr.co.soogong.master.domain.entity.profile.portfolio.IPortfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.Portfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.PriceByProject
import kr.co.soogong.master.domain.entity.profile.portfolio.RepairPhoto

open class PortfolioCommonViewHolder(
    private val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    open fun bind(
        context: Context,
        iPortfolio: IPortfolio,
        buttonLeftClickListener: (iPortfolio: IPortfolio) -> Unit,
        buttonRightClickListener: (iPortfolio: IPortfolio) -> Unit,
    ) {
        with(binding) {
            when (this) {
                is ViewHolderPortfolioBinding-> {
                    data = (iPortfolio as Portfolio)
                    setOnLeftButtonClick {
                        buttonLeftClickListener(iPortfolio)
                    }
                    setOnRightButtonClick {
                        buttonRightClickListener(iPortfolio)
                    }
                }

                is ViewHolderRepairPhotoBinding-> {
                    data = (iPortfolio as RepairPhoto)
                    setOnLeftButtonClick {
                        buttonLeftClickListener(iPortfolio)
                    }
                    setOnRightButtonClick {
                        buttonRightClickListener(iPortfolio)
                    }
                }

                is ViewHolderPriceByProjectBinding-> {
                    data = (iPortfolio as PriceByProject)
                    setOnLeftButtonClick {
                        buttonLeftClickListener(iPortfolio)
                    }
                    setOnRightButtonClick {
                        buttonRightClickListener(iPortfolio)
                    }
                }
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            viewType: Int,
        ): PortfolioCommonViewHolder =
            when (viewType) {
                0 -> PortfolioViewHolder(
                    ViewHolderPortfolioBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

                1 -> RepairPhotoViewHolder(
                    ViewHolderRepairPhotoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

                else -> PriceByProjectViewHolder(
                    ViewHolderPriceByProjectBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
    }
}

class PortfolioViewHolder(
    binding: ViewHolderPortfolioBinding,
) : PortfolioCommonViewHolder(binding)

class RepairPhotoViewHolder(
    binding: ViewHolderRepairPhotoBinding,
) : PortfolioCommonViewHolder(binding)

class PriceByProjectViewHolder(
    binding: ViewHolderPriceByProjectBinding,
) : PortfolioCommonViewHolder(binding)