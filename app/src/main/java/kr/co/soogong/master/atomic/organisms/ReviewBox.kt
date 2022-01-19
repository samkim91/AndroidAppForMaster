package kr.co.soogong.master.atomic.organisms

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
                binding.tvRecommendCount.text = it.toString()
            }
        }

    var reviewCount: Int? = null
        set(value) {
            field = value
            value?.let {
                binding.tvReviewCount.text =
                    if (it > 1000) resources.getString(R.string.over_one_thousand) else it.toString()
            }
        }

    var completionCount: Int? = null
        set(value) {
            field = value
            value?.let {
                binding.tvCompletionCount.text =
                    if (it > 1000) resources.getString(R.string.over_one_thousand) else it.toString()
            }
        }
}