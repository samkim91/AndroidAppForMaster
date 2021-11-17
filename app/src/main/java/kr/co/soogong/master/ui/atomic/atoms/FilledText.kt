package kr.co.soogong.master.ui.atomic.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.Theme
import kr.co.soogong.master.databinding.ViewFilledTextBinding

class FilledText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding = ViewFilledTextBinding.inflate(LayoutInflater.from(context), this, true)

    var content: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvContent.text = value
            }
        }

    var theme: Theme? = null
        set(value) {
            field = value
            value?.let {
                when (value) {
                    is Theme.Blue -> {
                        binding.tvContent.backgroundTintList =
                            ResourcesCompat.getColorStateList(resources, R.color.c_1A227ED4, null)
                        binding.tvContent.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.brand_blue,
                            null))
                    }
                    is Theme.Grey -> {
                        binding.tvContent.backgroundTintList =
                            ResourcesCompat.getColorStateList(resources, R.color.background, null)
                        binding.tvContent.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.grey_4,
                            null))
                    }
                    is Theme.Red -> {
                        binding.tvContent.backgroundTintList =
                            ResourcesCompat.getColorStateList(resources, R.color.brand_red, null)
                                ?.withAlpha(10)
                        binding.tvContent.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.brand_red,
                            null))
                    }
                }
            }
        }
}