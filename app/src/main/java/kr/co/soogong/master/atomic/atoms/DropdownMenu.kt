package kr.co.soogong.master.atomic.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewDropdownMenuBinding

class DropdownMenu @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IDropdownMenu {
    private var binding =
        ViewDropdownMenuBinding.inflate(LayoutInflater.from(context), this, true)

    val textInputLayout: TextInputLayout = binding.tilContainer
    val autoCompleteTextView: MaterialAutoCompleteTextView = binding.actvItem

    init {
        autoCompleteTextView.setDropDownBackgroundResource(R.drawable.bg_solid_white_stroke_light_grey2_radius6_padding16)
    }

    override var dropdownAdapter: ArrayAdapter<Any>? = null
        set(value) {
            field = value
            autoCompleteTextView.setAdapter(value)
        }

    override var dropdownError: String? = null
        set(value) {
            field = value
            textInputLayout.error = value
            textInputLayout.isErrorEnabled = !value.isNullOrBlank()
        }

    override var dropdownHint: String? = null
        set(value) {
            field = value
            textInputLayout.hint = value
            textInputLayout.isHintEnabled = !value.isNullOrBlank()
        }

    override var dropdownHelper: String? = null
        set(value) {
            field = value
            textInputLayout.helperText = value
            textInputLayout.isHelperTextEnabled = !value.isNullOrBlank()
        }

    override var dropdownInputType: Int? = null
        set(value) {
            field = value
            value?.let { autoCompleteTextView.inputType = value }
        }

    companion object {
        fun getWarrantyPeriods(): List<Pair<String, Int>> =
            listOf(
                Pair("보증기간 없음", -1),
                Pair("직접 입력", 0),
                Pair("1년", 1),
                Pair("2년", 2),
                Pair("3년", 3),
                Pair("4년", 4),
                Pair("5년", 5),
                Pair("6년", 6),
                Pair("7년", 7),
                Pair("8년", 8),
                Pair("9년", 9),
                Pair("10년", 10),
            )
    }
}