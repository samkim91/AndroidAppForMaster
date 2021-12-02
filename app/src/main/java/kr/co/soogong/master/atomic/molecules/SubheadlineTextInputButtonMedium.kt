package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.atomic.atoms.ButtonMedium
import kr.co.soogong.master.atomic.atoms.IButton
import kr.co.soogong.master.atomic.atoms.ITextInput
import kr.co.soogong.master.atomic.atoms.TextInput
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.ViewSubheadlineTextInputButtonMediumBinding

class SubheadlineTextInputButtonMedium @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput, IButton {
    private var binding =
        ViewSubheadlineTextInputButtonMediumBinding.inflate(LayoutInflater.from(context),
            this,
            true)

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    val textInput: TextInput = binding.tibmContent.textInput

    val buttonMedium: ButtonMedium = binding.tibmContent.buttonMedium

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