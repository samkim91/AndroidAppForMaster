package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.atomic.atoms.ITextInput
import kr.co.soogong.master.atomic.atoms.TextareaCounter
import kr.co.soogong.master.databinding.ViewSubheadlineTextareaCounterBinding

class SubheadlineTextareaCounter @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput {
    private var binding =
        ViewSubheadlineTextareaCounterBinding.inflate(LayoutInflater.from(context), this, true)

    private val textareaCounter: TextareaCounter = binding.tContent
    override val textInputLayout: TextInputLayout = binding.tContent.textInputLayout
    override val textInputEditText: TextInputEditText = binding.tContent.textInputEditText

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    override var hint: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvFootnote.text = it
                binding.tvFootnote.isVisible = true
            }
        }

    override var helper: String? = null
        set(value) {
            field = value
            value?.let { textareaCounter.helper = it }
        }

    override var error: String? = null
        set(value) {
            field = value
            textareaCounter.error = value
        }

    override var maxCount: Int? = null
        set(value) {
            field = value
            textareaCounter.maxCount = value
        }

    override var enabled: Boolean? = null
        set(value) {
            field = value
            textareaCounter.enabled = value
        }
}