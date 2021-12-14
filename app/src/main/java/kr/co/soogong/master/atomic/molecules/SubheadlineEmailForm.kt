package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.atomic.atoms.DropdownMenu
import kr.co.soogong.master.atomic.atoms.IDropdownMenu
import kr.co.soogong.master.atomic.atoms.ITextInput
import kr.co.soogong.master.atomic.atoms.TextInput
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

    val dropdownMenu: DropdownMenu = binding.dmDomain
    val dropdownTextInputLayout: TextInputLayout = dropdownMenu.textInputLayout
    val autoCompleteTextView: MaterialAutoCompleteTextView = dropdownMenu.autoCompleteTextView

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

    override var dropdownHint: String? = null
        set(value) {
            field = value
            dropdownMenu.dropdownHint = value
        }

    override var dropdownError: String? = null
        set(value) {
            field = value
            dropdownMenu.dropdownError = value
        }

    override var dropdownHelper: String? = null
        set(value) {
            field = value
            dropdownMenu.dropdownHelper = value
        }

    override var dropdownInputType: Int? = null
        set(value) {
            field = value
            dropdownMenu.dropdownInputType = value
        }

    var selectedItem: Pair<String, Int>? = null
        set(value) {
            field = value
            value?.let {
                autoCompleteTextView.setText(it.first, false)
            }
        }

    override var dropdownAdapter: ArrayAdapter<Any>? = null
        set(value) {
            field = value
            value?.let { dropdownMenu.dropdownAdapter = value }
        }
}