package kr.co.soogong.master.ui.widget

import android.content.Context
import android.icu.text.DecimalFormat
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

    var reviewCount: Int? = 0
        set(value) {
            field = value
            with(binding.reviewCount) {
                if (value == null || value == 0) {
                    text = resources.getString(R.string.special_symbol_stroke)
                    setTextColor(resources.getColor(R.color.color_C4C4C4, null))
                    return
                }
                text = value.toString()
                setTextColor(resources.getColor(R.color.color_1FC472, null))
            }
        }

    var recommendCount: Double? = 0.0
        set(value) {
            field = value
            with(binding.recommendCount) {
                if (value == null || value == 0.0) {
                    text = resources.getString(R.string.special_symbol_stroke)
                    setTextColor(resources.getColor(R.color.color_C4C4C4, null))
                    return
                }
                text = DecimalFormat("#.#").format(value)
//                text = value.let { round(it*10) / 10 }.toString()
                setTextColor(resources.getColor(R.color.color_1FC472, null))
            }
        }
}