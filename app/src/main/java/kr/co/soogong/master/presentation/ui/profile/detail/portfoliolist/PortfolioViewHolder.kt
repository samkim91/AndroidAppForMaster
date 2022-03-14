package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewHolderPortfolioBinding
import kr.co.soogong.master.databinding.ViewHolderPriceByProjectBinding
import kr.co.soogong.master.databinding.ViewHolderRepairPhotoBinding
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.domain.entity.profile.portfolio.IPortfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.Portfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.PriceByProject
import kr.co.soogong.master.domain.entity.profile.portfolio.RepairPhoto

open class PortfolioCommonViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    open fun binding(
        context: Context,
        iPortfolio: IPortfolio,
        buttonLeftClickListener: (iPortfolio: IPortfolio) -> Unit,
        buttonRightClickListener: (iPortfolio: IPortfolio) -> Unit,
    ) {

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
    private val binding: ViewHolderPortfolioBinding,
) : PortfolioCommonViewHolder(binding) {

    override fun binding(
        context: Context,
        iPortfolio: IPortfolio,
        buttonLeftClickListener: (iPortfolio: IPortfolio) -> Unit,
        buttonRightClickListener: (iPortfolio: IPortfolio) -> Unit,
    ) {
        with(binding) {
            data = (iPortfolio as Portfolio)

            buttonThemeLeft = ButtonTheme.OutlinedSecondary
            bmLeftButton.buttonText = context.getString(R.string.delete)

            setOnLeftButtonClick {
                buttonLeftClickListener(iPortfolio)
            }

            buttonThemeRight = ButtonTheme.OutlinedPrimary
            bmRightButton.buttonText = context.getString(R.string.modify)

            setOnRightButtonClick {
                buttonRightClickListener(iPortfolio)
            }
        }
    }
}

class RepairPhotoViewHolder(
    private val binding: ViewHolderRepairPhotoBinding,
) : PortfolioCommonViewHolder(binding) {

    override fun binding(
        context: Context,
        iPortfolio: IPortfolio,
        buttonLeftClickListener: (iPortfolio: IPortfolio) -> Unit,
        buttonRightClickListener: (iPortfolio: IPortfolio) -> Unit,
    ) {
        with(binding) {
            data = (iPortfolio as RepairPhoto)

            setOnLeftButtonClick {
                buttonLeftClickListener(iPortfolio)
            }

            setOnRightButtonClick {
                buttonRightClickListener(iPortfolio)
            }
        }
    }
}

class PriceByProjectViewHolder(
    private val binding: ViewHolderPriceByProjectBinding,
) : PortfolioCommonViewHolder(binding) {

    override fun binding(
        context: Context,
        iPortfolio: IPortfolio,
        buttonLeftClickListener: (iPortfolio: IPortfolio) -> Unit,
        buttonRightClickListener: (iPortfolio: IPortfolio) -> Unit,
    ) {
        with(binding) {
            data = (iPortfolio as PriceByProject)

            buttonThemeLeft = ButtonTheme.OutlinedSecondary
            bmLeftButton.buttonText = context.getString(R.string.delete)

            setOnLeftButtonClick {
                buttonLeftClickListener(iPortfolio)
            }

            buttonThemeRight = ButtonTheme.OutlinedPrimary
            bmRightButton.buttonText = context.getString(R.string.modify)

            setOnRightButtonClick {
                buttonRightClickListener(iPortfolio)
            }
        }
    }
}