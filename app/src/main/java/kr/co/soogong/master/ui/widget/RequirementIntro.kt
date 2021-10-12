package kr.co.soogong.master.ui.widget

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.databinding.ViewRequirementIntroBinding
import kr.co.soogong.master.ui.GRAY_THEME
import kr.co.soogong.master.ui.GREEN_THEME
import java.util.*

class RequirementIntro @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementIntroBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var subtitle: String? = ""
        set(value) {
            field = value
            binding.subtitle.isVisible = !value.isNullOrEmpty()
            binding.subtitle.text = value
        }

    var theme: Int? = 101
        set(value) {
            field = value
            when (value) {
                GREEN_THEME -> {
                    binding.title.setTextColor(context.getColor(R.color.color_1FC472))
                }
                else -> {
                    binding.title.setTextColor(context.getColor(R.color.color_555555))
                }
            }
        }
}