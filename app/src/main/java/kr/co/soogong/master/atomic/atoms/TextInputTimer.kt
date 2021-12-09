package kr.co.soogong.master.atomic.atoms

import android.content.Context
import android.os.CountDownTimer
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTextInputTimerBinding

class TextInputTimer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput {
    private var binding =
        ViewTextInputTimerBinding.inflate(LayoutInflater.from(context), this, true)

    val textInputLayout: TextInputLayout = binding.tilContainer
    val textInputEditText: TextInputEditText = binding.tieEdittext

    // Firebase SMS 인증 제한시간이 2분으로 default 타이머는 2분에, 1초씩 감소 / 필요 시, 원하는 값으로 재지정 필요
    private lateinit var timer: CountDownTimer

    fun initTimer(minute: Int, interval: Long, onTick: () -> Unit, onFinish: () -> Unit) {
        timer = object : CountDownTimer(minute * 60L * 1000, interval * 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                onTick()
                binding.tvTimer.text = resources.getString(R.string.timer_form,
                    millisUntilFinished / 1000 / 60,
                    millisUntilFinished / 1000 % 60)
            }

            override fun onFinish() {
                onFinish()
            }
        }
    }

    fun startTimer(onStart: () -> Unit) {
        onStart()
        timer.start()
    }

    fun stopTimer(onStop: () -> Unit) {
        onStop()
        timer.cancel()
    }

    override var error: String? = null
        set(value) {
            field = value
            textInputLayout.error = value
            textInputLayout.isErrorEnabled = !value.isNullOrBlank()
        }

    override var hint: String? = null
        set(value) {
            field = value
            textInputLayout.hint = value
            textInputLayout.isHintEnabled = !value.isNullOrBlank()
        }

    override var helper: String? = null
        set(value) {
            field = value
            textInputLayout.helperText = value
            textInputLayout.isHelperTextEnabled = !value.isNullOrBlank()
        }

    override var inputEnabled: Boolean? = null
        set(value) {
            field = value
            value?.let {
                with(textInputEditText) {
                    isEnabled = it
                    setTextColor(ResourcesCompat.getColor(resources, R.color.grey_3, null))
                    setBackgroundColor(ResourcesCompat.getColor(resources,
                        R.color.light_grey_1,
                        null))
                }
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

    override var max: Int? = null
        set(value) {
            field = value
            value?.let { textInputEditText.filters += InputFilter.LengthFilter(it) }      // Edittext 의 max 값 추가
        }

    override var inputType: Int? = null
        set(value) {
            field = value
            value?.let { textInputEditText.inputType = it }
        }
}