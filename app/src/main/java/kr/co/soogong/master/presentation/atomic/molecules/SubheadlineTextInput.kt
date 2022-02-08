package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.presentation.atomic.atoms.ITextInput
import kr.co.soogong.master.databinding.ViewSubheadlineTextInputBinding
import kr.co.soogong.master.utility.extension.formatWon

class SubheadlineTextInput @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput {
    private var binding =
        ViewSubheadlineTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    val textInputLayout: TextInputLayout = binding.tilContainer
    val textInputEditText: TextInputEditText = binding.tieEdittext

    var startIcon: Drawable? = null
        set(value) {
            field = value
            textInputLayout.startIconDrawable = value
        }

    var endIconMode: Int? = null
        set(value) {
            field = value
            value?.let { textInputLayout.endIconMode = it }
        }

    override var inputType: Int? = null
        set(value) {
            field = value
            value?.let { textInputEditText.inputType = it }
        }

    override var hint: String? = null
        set(value) {
            field = value
            textInputLayout.hint = value
        }

    override var helper: String? = null
        set(value) {
            field = value
            textInputLayout.helperText = value
        }

    override var error: String? = null
        set(value) {
            field = value
            textInputLayout.error = value
        }

    override var inputEnabled: Boolean? = null
        set(value) {
            field = value
            value?.let { textInputEditText.isEnabled = it }
        }

    override var maxCount: Int? = null
        set(value) {
            field = value
            value?.let {
                textInputLayout.isCounterEnabled = true
                textInputLayout.counterMaxLength = it
                this.max = it
            }
        }

    override var max: Int? = null
        set(value) {
            field = value
            value?.let { textInputEditText.filters += InputFilter.LengthFilter(it) }
        }

    companion object {
        @JvmStatic
        @BindingAdapter("moneyFormatAtHelper")
        fun SubheadlineTextInput.moneyFormatAtHelper(price: Long) {
            this@moneyFormatAtHelper.helper = price.formatWon()
        }
    }
}