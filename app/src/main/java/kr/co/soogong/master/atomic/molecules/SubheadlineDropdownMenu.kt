package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.atomic.atoms.IDropdownMenu
import kr.co.soogong.master.databinding.ViewSubheadlineDropdownMenuBinding

class SubheadlineDropdownMenu @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IDropdownMenu {
    private var binding =
        ViewSubheadlineDropdownMenuBinding.inflate(LayoutInflater.from(context), this, true)

    val textInputLayout: TextInputLayout = binding.tilContainer
    val autoCompleteTextView: AppCompatAutoCompleteTextView = binding.actvItem

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    var selectedItem: Pair<String, Int>? = null
        set(value) {
            field = value
            value?.let { autoCompleteTextView.setText(it.first, false) }
        }

    override var dropdownHint: String? = null
        set(value) {
            field = value
            textInputLayout.isHintEnabled = !value.isNullOrEmpty()
            textInputLayout.hint = value
        }

    override var dropdownError: String? = null
        set(value) {
            field = value
            textInputLayout.isErrorEnabled = !value.isNullOrEmpty()
            textInputLayout.error = value
        }

    override var dropdownHelper: String? = null
        set(value) {
            field = value
            textInputLayout.isHelperTextEnabled = !value.isNullOrEmpty()
            textInputLayout.helperText = value
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