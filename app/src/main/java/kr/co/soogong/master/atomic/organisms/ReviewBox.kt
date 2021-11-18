package kr.co.soogong.master.atomic.organisms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewReviewBoxBinding
import kr.co.soogong.master.utility.extension.formatDecimal

class ReviewBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewReviewBoxBinding.inflate(LayoutInflater.from(context), this, true)

    var recommendCount: Double? = 0.0
        set(value) {
            field = value
            with(binding.tvRecommendCount) {
                if (value == null || value == 0.0) {
                    text = resources.getString(R.string.special_symbol_stroke)
                    setTextColor(ResourcesCompat.getColor(resources, R.color.grey_1, null))
                    return
                }
                text = value.formatDecimal()
                setTextColor(ResourcesCompat.getColor(resources, R.color.brand_green, null))
            }
        }

    var reviewCount: Int? = 0
        set(value) {
            field = value
            with(binding.tvReviewCount) {
                if (value == null || value == 0) {
                    text = resources.getString(R.string.special_symbol_stroke)
                    setTextColor(ResourcesCompat.getColor(resources, R.color.grey_1, null))
                    return
                } else if (value > 1000) {
                    text = resources.getString(R.string.over_one_thousand)
                    setTextColor(ResourcesCompat.getColor(resources, R.color.brand_green, null))
                    return
                }
                text = value.toString()
                setTextColor(ResourcesCompat.getColor(resources, R.color.brand_green, null))
            }
        }

    var completionCount: Int? = 0
        set(value) {
            field = value
            with(binding.tvCompletionCount) {
                if (value == null || value == 0) {
                    text = resources.getString(R.string.special_symbol_stroke)
                    setTextColor(ResourcesCompat.getColor(resources, R.color.grey_1, null))
                    return
                } else if (value > 1000) {
                    text = resources.getString(R.string.over_one_thousand)
                    setTextColor(ResourcesCompat.getColor(resources, R.color.brand_green, null))
                    return
                }
                text = value.toString()
                setTextColor(ResourcesCompat.getColor(resources, R.color.brand_green, null))
            }
        }
}