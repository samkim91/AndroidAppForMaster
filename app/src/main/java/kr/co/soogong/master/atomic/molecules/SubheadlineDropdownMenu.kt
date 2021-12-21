package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintLayout
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
    val textInputLayout: TextInputLayout = dropdownMenu.textInputLayout
    val autoCompleteTextView: AppCompatAutoCompleteTextView = dropdownMenu.autoCompleteTextView

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

    override var dropdownAdapter: ArrayAdapter<Any>? = null
        set(value) {
            field = value
            value?.let { dropdownMenu.dropdownAdapter = value }
        }
}