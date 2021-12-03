package kr.co.soogong.master.atomic.organisms

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.atomic.atoms.TextInputButtonMedium
import kr.co.soogong.master.atomic.atoms.TextInputTimer
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.ViewSubheadlineTextInputButtonMediumTextInputTimerBinding

class SubheadlineTextInputButtonMediumTextInputTimer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewSubheadlineTextInputButtonMediumTextInputTimerBinding.inflate(LayoutInflater.from(
            context), this, true)

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    val textInputButtonMedium: TextInputButtonMedium = binding.tibmComponent
    val textInputTimer: TextInputTimer = binding.titComponent

    var textInputButtonMediumInputType: Int = InputType.TYPE_NULL
        set(value) {
            field = value
            textInputButtonMedium.textInput.inputType = value
        }

    var textInputButtonMediumHint: String? = null
        set(value) {
            field = value
            textInputButtonMedium.hint = value
        }

    var textInputButtonMediumError: String? = null
        set(value) {
            field = value
            textInputButtonMedium.error = value
        }

    var textInputButtonMediumHelper: String? = null
        set(value) {
            field = value
            textInputButtonMedium.helper = value
        }

    var textInputButtonMediumEnabled: Boolean? = null
        set(value) {
            field = value
            textInputButtonMedium.enabled = value
        }

    var textInputButtonMediumMaxCount: Int? = null
        set(value) {
            field = value
            textInputButtonMedium.maxCount = value
        }

    var textInputButtonMediumButtonText: String? = null
        set(value) {
            field = value
            textInputButtonMedium.buttonText = value
        }

    var textInputButtonMediumButtonEnabled: Boolean? = null
        set(value) {
            field = value
            textInputButtonMedium.buttonEnable = value
        }

    var textInputButtonMediumButtonTheme: ButtonTheme? = null
        set(value) {
            field = value
            textInputButtonMedium.buttonTheme = value
        }

    var textInputButtonMediumOnButtonClick: OnClickListener? = null
        set(value) {
            field = value
            textInputButtonMedium.onButtonClick = value
        }

    var contentTimerInputType: Int = InputType.TYPE_NULL
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
            textInputTimer.enabled = value
        }

    var textInputTimerMaxCount: Int? = null
        set(value) {
            field = value
            textInputTimer.maxCount = value
        }
}