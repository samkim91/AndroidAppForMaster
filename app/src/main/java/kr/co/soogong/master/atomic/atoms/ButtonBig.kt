package kr.co.soogong.master.atomic.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.ViewButtonBigBinding

class ButtonBig @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IButton {
    private val binding = ViewButtonBigBinding.inflate(LayoutInflater.from(context), this, true)

    override var buttonText: String? = null
        set(value) {
            field = value
            value?.let {
                binding.btButton.text = value
            }
        }

    override var buttonEnable: Boolean? = null
        set(value) {
            field = value
            value?.let {
                binding.btButton.isEnabled = value
            }
        }

    override var buttonTheme: ButtonTheme? = null
        set(value) {
            field = value
            value?.let {
                when (value) {
                    is ButtonTheme.Primary -> {
                        binding.btButton.background =
                            ResourcesCompat.getDrawable(resources,
                                R.drawable.background_selector_green_solid_radius8,
                                null)
                        binding.btButton.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.white,
                            null))
                    }
                    is ButtonTheme.Secondary -> {
                        binding.btButton.background =
                            ResourcesCompat.getDrawable(resources,
                                R.drawable.background_selector_light_grey1_solid_radius8,
                                null)
                        binding.btButton.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.selector_dark_grey1_alpha50,
                            null))
                    }
                    is ButtonTheme.Tertiary -> {
                        binding.btButton.background =
                            ResourcesCompat.getDrawable(resources,
                                R.drawable.background_selector_red_solid_radius8,
                                null)
                        binding.btButton.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.white,
                            null))
                    }
                    is ButtonTheme.OutlinedPrimary -> {
                        binding.btButton.background =
                            ResourcesCompat.getDrawable(resources,
                                R.drawable.background_selector_white_solid_green_stroke_radius8,
                                null)
                        binding.btButton.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.selector_green_alpha50,
                            null))
                    }
                    is ButtonTheme.OutlinedSecondary -> {
                        binding.btButton.background =
                            ResourcesCompat.getDrawable(resources,
                                R.drawable.background_selector_white_solid_light_grey2_stroke_radius8,
                                null)
                        binding.btButton.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.selector_dark_grey1_alpha50,
                            null))
                    }
                }
            }
        }

    override var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let {
                binding.btButton.setOnClickListener(value)
            }
        }
}