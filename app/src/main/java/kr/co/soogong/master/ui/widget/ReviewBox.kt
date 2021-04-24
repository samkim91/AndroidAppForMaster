package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.databinding.ViewReviewBoxBinding
import kr.co.soogong.master.databinding.ViewTitleChipGroupBinding

class ReviewBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewReviewBoxBinding.inflate(LayoutInflater.from(context), this, true)

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