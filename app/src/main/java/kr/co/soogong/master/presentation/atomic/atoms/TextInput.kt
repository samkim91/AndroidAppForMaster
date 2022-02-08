package kr.co.soogong.master.presentation.atomic.atoms

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTextInputBinding

class TextInput @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput {
    private var binding =
        ViewTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    val textInputLayout: TextInputLayout = binding.tilContainer
    val textInputEditText: TextInputEditText = binding.tieEdittext

    var startIcon: Drawable? = null
        set(value) {
            field = value
            textInputLayout.startIconDrawable = value
            textInputLayout.setStartIconTintList(
                ResourcesCompat.getColorStateList(resources, R.color.dark_grey_1, null)
            )
        }

    var endIcon: Int? = null
        set(value) {
            field = value
            textInputLayout.endIconMode = value ?: END_ICON_CLEAR_TEXT
        }

    override var error: String? = null
        set(value) {
            field = value
            textInputLayout.error = value
            textInputLayout.isErrorEnabled = !value.isNullOrEmpty()
        }

    override var hint: String? = null
        set(value) {
            field = value
            textInputLayout.hint = value
            textInputLayout.isHintEnabled = !value.isNullOrEmpty()
        }

    override var helper: String? = null
        set(value) {
            field = value
            textInputLayout.helperText = value
            textInputLayout.isHelperTextEnabled = !value.isNullOrEmpty()
        }

    override var inputEnabled: Boolean? = null
        set(value) {
            field = value
            value?.let {
                with(textInputEditText) {
                    isEnabled = it
                }
            }
        }

    override var max: Int? = null
        set(value) {
            field = value
            value?.let {
                textInputEditText.filters += InputFilter.LengthFilter(it)      // Edittext 의 max 값 추가
            }
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

    override var inputType: Int? = null
        set(value) {
            field = value
            value?.let { textInputEditText.inputType = it }
        }
}