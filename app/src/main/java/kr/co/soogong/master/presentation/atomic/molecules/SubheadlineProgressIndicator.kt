package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.common.ColorTheme
import kr.co.soogong.master.databinding.ViewSubheadlineProgressIndicatorBinding
import kotlin.math.roundToInt

class SubheadlineProgressIndicator @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    val binding =
        ViewSubheadlineProgressIndicatorBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvSubheadline.text = it
            }
        }

    var contentLabelFilled: String? = null
        set(value) {
            field = value
            value?.let {
                binding.lfLabel.content = it
            }
        }

    var colorThemeLabelFilled: ColorTheme? = null
        set(value) {
            field = value
            value?.let {
                binding.lfLabel.colorTheme = it
            }
        }

    var percent: Double? = null
        set(value) {
            field = value
            value?.let { double ->
                double.roundToInt().let { int ->
                    binding.tvPercent.text = resources.getString(R.string.percent, int)
                    binding.lpiProgress.progress = int
                }
            }
        }
}