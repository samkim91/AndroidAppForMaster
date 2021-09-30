package kr.co.soogong.master.ui.widget

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewRequirementDueDateBinding
import kr.co.soogong.master.databinding.ViewRequirementIntroBinding
import kr.co.soogong.master.ui.GREEN_THEME
import java.util.*

class RequirementDueDate @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementDueDateBinding.inflate(LayoutInflater.from(context), this, true)

    var label: String? = ""
        set(value) {
            field = value
            binding.dueDateLabel.text = value
        }

    var date: Date? = Date()
        set(value) {
            field = value
            binding.dueDate.text = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA).format(value)
        }
}