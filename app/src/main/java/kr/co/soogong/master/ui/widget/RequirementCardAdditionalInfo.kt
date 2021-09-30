package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewRequirementCardAdditionalInfoBinding
import kr.co.soogong.master.ui.*

class RequirementCardAdditionalInfo @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementCardAdditionalInfoBinding.inflate(LayoutInflater.from(context), this, true)

    fun setLayout(
        theme: Int,
        type: Int,
        titleData: String?,
        contentData: String?,
        alertData: String
    ) {
        setTheme(theme)
        setType(type)

        with(binding) {
            key = titleData
            value = contentData
            alert = alertData

            alertTextview.isVisible = alertData.isNotEmpty()
        }
    }

    private fun setTheme(theme: Int) {
        with(binding) {
            when (theme) {
                ORANGE_THEME -> {
                    icon.imageTintList =
                        ColorStateList.valueOf(context.getColor(R.color.color_FF711D))
                    keyTextview.setTextColor(context.getColor(R.color.color_FF711D))
                    valueTextview.setTextColor(context.getColor(R.color.color_FF711D))
                }
                GREEN_THEME -> {
                    icon.imageTintList =
                        ColorStateList.valueOf(context.getColor(R.color.color_0C5E47))
                    keyTextview.setTextColor(context.getColor(R.color.color_0C5E47))
                    valueTextview.setTextColor(context.getColor(R.color.color_0C5E47))
                }
                GRAY_THEME -> {
                    icon.imageTintList =
                        ColorStateList.valueOf(context.getColor(R.color.color_909090))
                    keyTextview.setTextColor(context.getColor(R.color.color_555555))
                    valueTextview.setTextColor(context.getColor(R.color.color_555555))
                }
            }
        }
    }

    private fun setType(type: Int) {
        with(binding) {
            when (type) {
                MONEY_TYPE -> {
                    icon.setImageResource(R.drawable.ic_won)
                }
                CALENDAR_TYPE -> {
                    icon.setImageResource(R.drawable.ic_calendar)
                }
            }
        }
    }

    companion object {
        fun setContainerTheme(context: Context, container: LinearLayoutCompat, theme: Int) {
            container.isVisible = true
            when(theme) {
                ORANGE_THEME -> {
                    container.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.color_FFF2EB))
                    container.dividerDrawable.setTintList(ColorStateList.valueOf(context.getColor(R.color.color_FFE1D2)))
                }
                GREEN_THEME -> {
                    container.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.color_F0FCF2))
                    container.dividerDrawable.setTintList(ColorStateList.valueOf(context.getColor(R.color.color_D4FADB)))
                }
                else -> {
                    container.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.color_F8F8F8))
                    container.dividerDrawable.setTintList(ColorStateList.valueOf(context.getColor(R.color.color_E3E3E3)))
                }
            }
        }
    }
}