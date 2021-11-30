package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.atomic.atoms.ITextInput
import kr.co.soogong.master.atomic.atoms.TextInput
import kr.co.soogong.master.databinding.ViewSubheadlineTextInputBinding

class SubheadlineTextInput @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput {
    private var binding =
        ViewSubheadlineTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    val textInput: TextInput = binding.tiContent
    override val textInputLayout: TextInputLayout = textInput.textInputLayout
    override val textInputEditText: TextInputEditText = textInput.textInputEditText

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    var inputType: Int? = null
        set(value) {
            field = value
            value?.let { textInputEditText.inputType = it }
        }

    override var hint: String? = null
        set(value) {
            field = value
            textInput.hint = value
        }

    override var helper: String? = null
        set(value) {
            field = value
            textInput.helper = value
        }

    override var error: String? = null
        set(value) {
            field = value
            textInput.error = value
        }

    override var enabled: Boolean? = null
        set(value) {
            field = value
            value?.let { textInput.enabled = it }
        }

    override var maxCount: Int? = null
        set(value) {
            field = value
            value?.let { textInput.maxCount = it }
        }
}