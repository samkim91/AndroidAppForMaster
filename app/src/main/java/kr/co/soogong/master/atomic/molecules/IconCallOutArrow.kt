package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.global.ColorTheme
import kr.co.soogong.master.databinding.ViewIconSubheadlineArrowBinding

class IconCallOutArrow @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewIconSubheadlineArrowBinding.inflate(LayoutInflater.from(context), this, true)

    var icon: Drawable? = null
        set(value) {
            field = value
            value?.let {
                binding.ivIcon.isVisible = true
                binding.ivIcon.background = it
            }
        }

    var callOut: String? = null
        set(value) {
            field = value
            value?.let { binding.tvTitle.text = it }
        }

    var endText: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvEndText.text = it
                binding.tvEndText.isVisible = true
            }
        }

    var colorTheme: ColorTheme? = null
        set(value) {
            field = value
            value?.let {
                when (it) {
                    is ColorTheme.Grey -> {

                    }
                    is ColorTheme.Blue -> {
                    }
                    is ColorTheme.Red -> {
                    }
                    is ColorTheme.Green -> {
                        this.background = ResourcesCompat.getDrawable(resources,
                            R.drawable.bg_solid_white_stroke_green_radius8,
                            null)
                        binding.tvTitle.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.brand_green,
                            null))
                        binding.ivIcon.backgroundTintList =
                            ResourcesCompat.getColorStateList(resources, R.color.brand_green, null)
                        binding.ivArrow.backgroundTintList =
                            ResourcesCompat.getColorStateList(resources, R.color.brand_green, null)
                    }
                }
            }
        }
}