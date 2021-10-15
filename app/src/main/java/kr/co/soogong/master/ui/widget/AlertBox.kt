package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewAlertBoxBinding
import kr.co.soogong.master.ui.GREEN_THEME
import kr.co.soogong.master.ui.ORANGE_THEME
import kr.co.soogong.master.ui.SPEAKER_TYPE

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
            binding.arrowButton.backgroundTintList =
                ColorStateList.valueOf(context.getColor(R.color.basic_white))
            when (value) {
                ORANGE_THEME -> {
                    this.setBackgroundResource(R.drawable.shape_light_orange_background_light_orange_border_radius8)
                    binding.alertTitle.setTextColor(context.getColor(R.color.color_FF711D))
                    binding.typeIcon.backgroundTintList =
                        ColorStateList.valueOf(context.getColor(R.color.color_FF711D))
                }
                GREEN_THEME -> {
                    this.setBackgroundResource(R.drawable.shape_light_green_background_light_green_border_radius8)
                    binding.alertTitle.setTextColor(context.getColor(R.color.color_1FB571))
                    binding.typeIcon.backgroundTintList =
                        ColorStateList.valueOf(context.getColor(R.color.color_1FB571))
                }
                else -> {
                    this.setBackgroundResource(R.drawable.shape_light_grey_background_light_grey_border_radius8)
                    binding.alertTitle.setTextColor(context.getColor(R.color.color_616161))
                    binding.typeIcon.backgroundTintList =
                        ColorStateList.valueOf(context.getColor(R.color.color_616161))
                }
            }
        }

    var icon: Int = 300
        set(value) {
            field = value
            binding.typeIcon.setBackgroundResource(
                when (value) {
                    SPEAKER_TYPE -> R.drawable.ic_speaker
                    else -> R.drawable.ic_document
                }
            )
        }

    var alertTitle: String? = ""
        set(value) {
            field = value
            binding.alertTitle.text = value
        }

    var alertContent: String? = null
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                binding.line.isVisible = true
                binding.alertContent.isVisible = true
                binding.alertContent.text = value
            } else {
                binding.line.isVisible = false
                binding.alertContent.isVisible = false
            }
        }

    var arrowVisibility: Boolean? = false
        set(value) {
            field = value
            value?.let {
                binding.arrowButton.isVisible = value
            }
        }
}