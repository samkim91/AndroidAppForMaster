package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.databinding.ViewTitleSwitchcompatBinding
import timber.log.Timber

class TitleSwitchCompat @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewTitleSwitchcompatBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var subtitle: String? = ""
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                binding.subtitle.isVisible = true
                binding.subtitle.text = value
            }
        }

    var checked: Boolean = false
        set(value) {
            field = value
            binding.switchCompat.isChecked = value
        }
        get() {
            field = binding.switchCompat.isChecked
            return field
        }

    var doubleArrowVisibility: Boolean = false
        set(value) {
            field = value
            binding.doubleArrow.isVisible = value
        }

    var underLine: Boolean = true
        set(value) {
            field = value
            binding.line.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    fun setSwitchClick(listener: CompoundButton.OnCheckedChangeListener) {
        binding.switchCompat.setOnCheckedChangeListener(listener)
    }

    fun setLayoutForRequestMeasure(masterDto: MasterDto) {
        Timber.tag("TitleSwitchCompat").d("setLayoutForReceivingRequestMeasure: ")
        with(binding) {
            if (masterDto.secretaryYn != true || masterDto.freeMeasureYn != true) {
                this@TitleSwitchCompat.isVisible = false
                return
            }

            this@TitleSwitchCompat.isVisible = true
            title.setTextAppearance(R.style.text_style_16sp_bold)
            title.setTextColor(resources.getColor(R.color.c_FFFFFF, null))

            masterDto.requestMeasureYn?.let { changeTextAndBackgroundForRequestMeasure(it) }
        }
    }

    fun changeTextAndBackgroundForRequestMeasure(requestMeasureYn: Boolean) {
        Timber.tag("TitleSwitchCompat").d("changeTextAndBackgroundForRequestMeasure: $requestMeasureYn")
        with(binding) {
            if (requestMeasureYn) {
                container.setBackgroundColor(resources.getColor(R.color.c_1B8C61, null))
                this@TitleSwitchCompat.title =
                    resources.getString(R.string.accepting_request_measurement)
                this@TitleSwitchCompat.doubleArrowVisibility = false
                checked = true
            } else {
                container.setBackgroundColor(resources.getColor(R.color.c_08362F, null))
                this@TitleSwitchCompat.title =
                    resources.getString(R.string.holding_request_measurement)
                this@TitleSwitchCompat.doubleArrowVisibility = true
                checked = false
            }
        }
    }
}