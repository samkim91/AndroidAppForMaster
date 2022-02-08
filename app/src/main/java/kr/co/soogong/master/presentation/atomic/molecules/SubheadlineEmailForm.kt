package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.presentation.atomic.atoms.IDropdownMenu
import kr.co.soogong.master.presentation.atomic.atoms.ITextInput
import kr.co.soogong.master.presentation.atomic.atoms.TextInput
import kr.co.soogong.master.databinding.ViewSubheadlineEmailFormBinding

class SubheadlineEmailForm @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput, IDropdownMenu {
    private var binding =
        ViewSubheadlineEmailFormBinding.inflate(LayoutInflater.from(context), this, true)

    val textInput: TextInput = binding.tiLocalPart
    val textInputLayout: TextInputLayout = textInput.textInputLayout
    val textInputEditText: TextInputEditText = textInput.textInputEditText

    val dropdownTextInputLayout: TextInputLayout = binding.tilContainer
    val autoCompleteTextView: AppCompatAutoCompleteTextView = binding.actvItem

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    override var inputType: Int? = null
        set(value) {
            field = value
            textInput.inputType = value
        }

    override var error: String? = null
        set(value) {
            field = value
            textInput.error = value
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

    override var inputEnabled: Boolean? = null
        set(value) {
            field = value
            textInput.inputEnabled = value
        }

    override var maxCount: Int? = null
        set(value) {
            field = value
            textInput.maxCount = value
        }

    override var max: Int? = null
        set(value) {
            field = value
            textInput.max = value
        }

    var selectedItem: Pair<String, Int>? = null
        set(value) {
            field = value
            value?.let {
                autoCompleteTextView.setText(it.first, false)
            }
        }

    override var dropdownHint: String? = null
        set(value) {
            field = value
            dropdownTextInputLayout.isHintEnabled = !value.isNullOrEmpty()
            dropdownTextInputLayout.hint = value
        }

    override var dropdownError: String? = null
        set(value) {
            field = value
            dropdownTextInputLayout.isErrorEnabled = !value.isNullOrEmpty()
            dropdownTextInputLayout.error = value
        }

    override var dropdownHelper: String? = null
        set(value) {
            field = value
            dropdownTextInputLayout.isHelperTextEnabled = !value.isNullOrEmpty()
            dropdownTextInputLayout.helperText = value
        }

    override var dropdownInputType: Int? = null
        set(value) {
            field = value
            value?.let { autoCompleteTextView.inputType = value }
        }

    override var dropdownAdapter: ArrayAdapter<Any>? = null
        set(value) {
            field = value
            value?.let { autoCompleteTextView.setAdapter(value) }
        }
}