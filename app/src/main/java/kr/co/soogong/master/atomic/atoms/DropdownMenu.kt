package kr.co.soogong.master.atomic.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.databinding.ViewDropdownMenuBinding

class DropdownMenu @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IDropdownMenu {
    private var binding =
        ViewDropdownMenuBinding.inflate(LayoutInflater.from(context), this, true)

    override val textInputLayout: TextInputLayout = binding.tilContainer
    override val autoCompleteTextView: MaterialAutoCompleteTextView = binding.actvItem

    override var adapter: ArrayAdapter<Any>? = null
        set(value) {
            field = value
            autoCompleteTextView.setAdapter(value)
        }
}