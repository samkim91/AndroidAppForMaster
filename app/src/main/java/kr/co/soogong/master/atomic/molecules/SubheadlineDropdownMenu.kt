package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.atomic.atoms.DropdownMenu
import kr.co.soogong.master.atomic.atoms.IDropdownMenu
import kr.co.soogong.master.databinding.ViewSubheadlineDropdownMenuBinding

class SubheadlineDropdownMenu @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IDropdownMenu {
    private var binding =
        ViewSubheadlineDropdownMenuBinding.inflate(LayoutInflater.from(context), this, true)

    val dropdownMenu: DropdownMenu = binding.dmItems
    override val textInputLayout: TextInputLayout = dropdownMenu.textInputLayout
    override val autoCompleteTextView: MaterialAutoCompleteTextView =
        dropdownMenu.autoCompleteTextView

    override var adapter: ArrayAdapter<Any>? = null
        set(value) {
            field = value
            value?.let { dropdownMenu.adapter = value }
        }

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

    var inputType: Int? = null
        set(value) {
            field = value
            value?.let { autoCompleteTextView.inputType = it }
        }

    var helper: String? = null
        set(value) {
            field = value
            value?.let { textInputLayout.helperText = it }
        }
}