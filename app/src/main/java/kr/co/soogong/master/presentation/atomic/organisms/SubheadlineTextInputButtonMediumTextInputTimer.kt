package kr.co.soogong.master.presentation.atomic.organisms

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.presentation.atomic.atoms.IButton
import kr.co.soogong.master.presentation.atomic.atoms.ITextInput
import kr.co.soogong.master.presentation.atomic.atoms.TextInputButtonMedium
import kr.co.soogong.master.presentation.atomic.atoms.TextInputTimer
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.databinding.ViewSubheadlineTextInputButtonMediumTextInputTimerBinding

class SubheadlineTextInputButtonMediumTextInputTimer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput, IButton {
    private var binding =
        ViewSubheadlineTextInputButtonMediumTextInputTimerBinding.inflate(LayoutInflater.from(
            context), this, true)

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvSubheadline.isVisible = true
                binding.tvSubheadline.text = value
            }
        }

    val textInputButtonMedium: TextInputButtonMedium = binding.tibmComponent
    val textInputTimer: TextInputTimer = binding.titComponent

    // 첫 번째 row ( TextInput + Button )에 대한 setters
    override var error: String? = null
        set(value) {
            field = value
            textInputButtonMedium.error = value
        }

    override var hint: String? = null
        set(value) {
            field = value
            textInputButtonMedium.hint = value
        }

    override var helper: String? = null
        set(value) {
            field = value
            textInputButtonMedium.helper = value
        }
    override var inputEnabled: Boolean? = null
        set(value) {
            field = value
            textInputButtonMedium.inputEnabled = value
        }
    override var maxCount: Int? = null
        set(value) {
            field = value
            textInputButtonMedium.maxCount = value
        }

    override var max: Int? = null
        set(value) {
            field = value
            textInputButtonMedium.max = value
        }

    override var inputType: Int? = null
        set(value) {
            field = value
            textInputButtonMedium.inputType = value
        }

    override var buttonText: String? = null
        set(value) {
            field = value
            textInputButtonMedium.buttonText = value
        }
    override var buttonEnable: Boolean? = null
        set(value) {
            field = value
            textInputButtonMedium.buttonEnable = value
        }
    override var buttonTheme: ButtonTheme? = null
        set(value) {
            field = value
            textInputButtonMedium.buttonTheme = value
        }
    override var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            textInputButtonMedium.onButtonClick = value
        }

    var textInputButtonMediumInputType: Int = InputType.TYPE_NULL
        set(value) {
            field = value
            textInputButtonMedium.textInput.inputType = value
        }

    // 두 번째 row (TextInput + Timer) 에 대한 setters
    var textInputTimerInputType: Int = InputType.TYPE_NULL
        set(value) {
            field = value
            textInputTimer.textInputEditText.inputType = value
        }

    var textInputTimerHint: String? = null
        set(value) {
            field = value
            textInputTimer.hint = value
        }

    var textInputTimerError: String? = null
        set(value) {
            field = value
            textInputTimer.error = value
        }

    var textInputTimerHelper: String? = null
        set(value) {
            field = value
            textInputTimer.helper = value
        }

    var textInputTimerEnabled: Boolean? = null
        set(value) {
            field = value
            textInputTimer.inputEnabled = value
        }

    var textInputTimerMaxCount: Int? = null
        set(value) {
            field = value
            textInputTimer.maxCount = value
        }

    var textInputTimerMax: Int? = null
        set(value) {
            field = value
            textInputTimer.max = value
        }
}