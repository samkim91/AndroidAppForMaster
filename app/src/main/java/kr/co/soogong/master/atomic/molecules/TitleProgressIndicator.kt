package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ColorTheme
import kr.co.soogong.master.databinding.ViewTitleProgressIndicatorBinding
import kotlin.math.roundToInt

class TitleProgressIndicator @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    val binding =
        ViewTitleProgressIndicatorBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvTitle.text = it
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
                    binding.tvPercent.text = resources.getString(R.string.percent, int.toString())
                    binding.lpiProgress.progress = int
                }
            }
        }
}