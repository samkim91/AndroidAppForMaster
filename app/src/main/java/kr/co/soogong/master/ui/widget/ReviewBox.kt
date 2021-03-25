package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewReviewBinding

class ReviewLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewReviewBinding.inflate(LayoutInflater.from(context), this, false)

    var reviewCount: Int? = 0
        set(value) {
            field = value
            binding.reviewCount.text = value.toString()
        }

    var recommendCount: Double? = 0.0
        set(value) {
            field = value
            binding.recommendCount.text = value.toString()
        }
}
