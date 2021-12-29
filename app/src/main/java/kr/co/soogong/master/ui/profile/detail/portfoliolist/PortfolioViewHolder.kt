package kr.co.soogong.master.ui.profile.detail.portfoliolist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.databinding.ViewHolderPortfolioBinding
import kr.co.soogong.master.databinding.ViewHolderPriceByProjectBinding

open class PortfolioCommonViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    open fun binding(
        context: Context,
        portfolioDto: PortfolioDto,
        buttonLeftClickListener: (id: Int) -> Unit,
        buttonRightClickListener: (id: Int) -> Unit,
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
        buttonRightClickListener: (id: Int) -> Unit,
    ) {
        with(binding) {
            data = portfolioDto

            setOnLeftButtonClick {
                portfolioDto.id?.let {
                    buttonLeftClickListener(it)
                }
            }

            buttonThemeLeft = ButtonTheme.OutlinedSecondary
            bmLeftButton.buttonText = context.getString(R.string.delete)

            bmRightButton.buttonText = context.getString(R.string.modify)
            buttonThemeRight = ButtonTheme.OutlinedPrimary

            setOnRightButtonClick {
                portfolioDto.id?.let {
                    buttonRightClickListener(it)
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
        buttonRightClickListener: (id: Int) -> Unit,
    ) {
        with(binding) {
            data = portfolioDto

            setOnLeftButtonClick {
                portfolioDto.id?.let {
                    buttonLeftClickListener(it)
                }
            }

            buttonThemeLeft = ButtonTheme.OutlinedSecondary
            bmLeftButton.buttonText = context.getString(R.string.delete)

            bmRightButton.buttonText = context.getString(R.string.modify)
            buttonThemeRight = ButtonTheme.OutlinedPrimary

            setOnRightButtonClick {
                portfolioDto.id?.let {
                    buttonRightClickListener(it)
                }
            }
        }
    }
}