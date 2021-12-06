package kr.co.soogong.master.atomic.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.ViewTextInputButtonMediumBinding

class TextInputButtonMedium @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput, IButton {
    private var binding =
        ViewTextInputButtonMediumBinding.inflate(LayoutInflater.from(context), this, true)

    val textInput: TextInput = binding.tiContent

    val buttonMedium: ButtonMedium = binding.bbButton

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

    override var enabled: Boolean? = null
        set(value) {
            field = value
            textInput.enabled = value
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

    override var inputType: Int? = null
        set(value) {
            field = value
            textInput.inputType = value
        }

    override var buttonText: String? = null
        set(value) {
            field = value
            buttonMedium.buttonText = value
        }

    override var buttonEnable: Boolean? = null
        set(value) {
            field = value
            buttonMedium.buttonEnable = value
        }

    override var buttonTheme: ButtonTheme? = null
        set(value) {
            field = value
            buttonMedium.buttonTheme = value
        }

    override var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            buttonMedium.onButtonClick = value
        }
}