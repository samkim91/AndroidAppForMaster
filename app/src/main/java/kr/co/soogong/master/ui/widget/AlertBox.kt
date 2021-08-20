package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewAlertBoxBinding
import kr.co.soogong.master.databinding.ViewRequirementIntroBinding
import kr.co.soogong.master.ui.GREEN_THEME
import kr.co.soogong.master.ui.ORANGE_THEME

class AlertBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewAlertBoxBinding.inflate(LayoutInflater.from(context), this, true)

    var theme: Int = 100
        set(value) {
            field = value
            when (value) {
                ORANGE_THEME -> {
                    this.setBackgroundResource(R.drawable.shape_fill_orange_border_radius8)
                    binding.alertTitle.setTextColor(context.getColor(R.color.color_FF711D))
                    binding.speakerIcon.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.color_FF711D))
                }
                else -> {
                    this.setBackgroundResource(R.drawable.shape_fill_green_border_radius8)
                    binding.alertTitle.setTextColor(context.getColor(R.color.color_1FB571))
                    binding.speakerIcon.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.color_1FB571))
                }
            }
        }

    var alertTitle: String? = ""
        set(value) {
            field = value
            binding.alertTitle.text = value
        }

    var alertContent: String? = ""
        set(value) {
            field = value
            binding.alertContent.text = value
        }
}