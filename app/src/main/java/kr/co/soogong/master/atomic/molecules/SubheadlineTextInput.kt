package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.atomic.atoms.TextInput
import kr.co.soogong.master.databinding.ViewSubheadlineTextInputBinding

class SubheadlineTextInput @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewSubheadlineTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    val textInput: TextInput = binding.tiContent
    val textInputLayout: TextInputLayout = textInput.textInputLayout
    val textInputEditText: TextInputEditText = textInput.textInputEditText

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    var hint: String? = null
        set(value) {
            field = value
            value?.let { textInputLayout.hint = it }
        }

    var enabled: Boolean? = null
        set(value) {
            field = value
            value?.let { textInput.enabled = it }
        }

    var helper: String? = null
        set(value) {
            field = value
            value?.let { textInputLayout.helperText = it }
        }

    var maxCount: Int = 0
        set(value) {
            field = value
            textInput.maxCount = value
        }

    var error: String? = null
        set(value) {
            field = value
            value?.let { textInputLayout.error = it }
        }
}