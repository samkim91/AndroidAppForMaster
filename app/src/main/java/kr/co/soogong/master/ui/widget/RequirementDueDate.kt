package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewRequirementDueDateBinding

class RequirementDueDate @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementDueDateBinding.inflate(LayoutInflater.from(context), this, true)

    var label: String? = ""
        set(value) {
            field = value
            binding.dueDateLabel.text = value
        }

    var date: String? = ""
        set(value) {
            field = value
            binding.dueDate.text = value
        }
}