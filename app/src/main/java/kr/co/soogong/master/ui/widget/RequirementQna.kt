package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewRequirementQnaBinding

class RequirementQna @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementQnaBinding.inflate(LayoutInflater.from(context), this, true)

    var question: String? = ""
        set(value) {
            field = value
            binding.question.text = value
        }

    var answer: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                binding.answer.setText(R.string.null_text)
            } else {
                binding.answer.text = value
            }
        }
}