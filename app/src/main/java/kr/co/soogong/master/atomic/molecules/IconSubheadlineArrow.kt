package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ViewIconSubheadlineArrowBinding

class IconSubheadlineArrow @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewIconSubheadlineArrowBinding.inflate(LayoutInflater.from(context), this, true)

    var icon: Drawable? = null
        set(value) {
            field = value
            value?.let { binding.ivIcon.background = it }
        }

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvTitle.text = it }
        }

    var endText: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvEndText.text = it
                binding.tvEndText.isVisible = true
            }
        }
}