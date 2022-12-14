package kr.co.soogong.master.presentation.atomic.organisms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewReviewBoxBinding

class ReviewBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewReviewBoxBinding.inflate(LayoutInflater.from(context), this, true)

    var recommendation: Float? = null
        set(value) {
            field = value
            value?.let {
                binding.tvRecommendCount.text = when {
                    it > 1000 -> resources.getString(R.string.over_one_thousand)
                    it == 0.0f -> resources.getString(R.string.special_symbol_stroke)
                    else -> it.toString()
                }
            }
        }

    var reviewCount: Int? = null
        set(value) {
            field = value
            value?.let {
                binding.tvReviewCount.text = when {
                        it > 1000 -> resources.getString(R.string.over_one_thousand)
                        it == 0 -> resources.getString(R.string.special_symbol_stroke)
                        else -> it.toString()
                    }
            }
        }

    var completionCount: Int? = null
        set(value) {
            field = value
            value?.let {
                binding.tvCompletionCount.text = when {
                        it > 1000 -> resources.getString(R.string.over_one_thousand)
                        it == 0 -> resources.getString(R.string.special_symbol_stroke)
                        else -> it.toString()
                    }
            }
        }
}