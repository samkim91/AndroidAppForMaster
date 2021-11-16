package kr.co.soogong.master.ui.atomic.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.Theme
import kr.co.soogong.master.databinding.ViewFilledTextBinding

class FilledText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : AppCompatTextView(context, attributeSet, defStyle) {
    private val binding = ViewFilledTextBinding.inflate(LayoutInflater.from(context))

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
                        backgroundTintList = ResourcesCompat.getColorStateList(resources, R.color.c_1A227ED4, null)
                        setTextColor(ResourcesCompat.getColor(resources, R.color.brand_blue, null))
                    }
                    is Theme.Grey -> {
                        backgroundTintList = ResourcesCompat.getColorStateList(resources, R.color.background, null)?.withAlpha(10)
                        setTextColor(ResourcesCompat.getColor(resources, R.color.grey_4, null))
                    }
                    is Theme.Red -> {
                        backgroundTintList = ResourcesCompat.getColorStateList(resources, R.color.brand_red, null)?.withAlpha(10)
                        setTextColor(ResourcesCompat.getColor(resources, R.color.brand_red, null))
                    }
                }
            }
        }
}