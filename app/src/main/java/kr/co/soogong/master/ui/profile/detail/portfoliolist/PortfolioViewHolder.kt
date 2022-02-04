package kr.co.soogong.master.ui.profile.detail.portfoliolist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.global.ButtonTheme
import kr.co.soogong.master.databinding.ViewHolderPortfolioBinding
import kr.co.soogong.master.databinding.ViewHolderPriceByProjectBinding

open class PortfolioCommonViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    open fun binding(
        context: Context,
        portfolioDto: PortfolioDto,
        buttonLeftClickListener: (id: Int) -> Unit,
        buttonRightClickListener: (portfolioDto: PortfolioDto) -> Unit,
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
        portfolioDto: PortfolioDto,
        buttonLeftClickListener: (id: Int) -> Unit,
        buttonRightClickListener: (portFolioDto: PortfolioDto) -> Unit,
    ) {
        with(binding) {
            data = portfolioDto

            buttonThemeLeft = ButtonTheme.OutlinedSecondary
            bmLeftButton.buttonText = context.getString(R.string.delete)

            setOnLeftButtonClick {
                portfolioDto.id?.let {
                    buttonLeftClickListener(it)
                }
            }

            buttonThemeRight = ButtonTheme.OutlinedPrimary
            bmRightButton.buttonText = context.getString(R.string.modify)

            setOnRightButtonClick {
                portfolioDto.id?.let {
                    buttonRightClickListener(portfolioDto)
                }
            }
        }
    }
}

class PriceByProjectViewHolder(
    private val binding: ViewHolderPriceByProjectBinding,
) : PortfolioCommonViewHolder(binding) {

    override fun binding(
        context: Context,
        portfolioDto: PortfolioDto,
        buttonLeftClickListener: (id: Int) -> Unit,
        buttonRightClickListener: (portfolioDto: PortfolioDto) -> Unit,
    ) {
        with(binding) {
            data = portfolioDto

            buttonThemeLeft = ButtonTheme.OutlinedSecondary
            bmLeftButton.buttonText = context.getString(R.string.delete)

            setOnLeftButtonClick {
                portfolioDto.id?.let {
                    buttonLeftClickListener(it)
                }
            }

            buttonThemeRight = ButtonTheme.OutlinedPrimary
            bmRightButton.buttonText = context.getString(R.string.modify)

            setOnRightButtonClick {
                portfolioDto.id?.let {
                    buttonRightClickListener(portfolioDto)
                }
            }
        }
    }
}