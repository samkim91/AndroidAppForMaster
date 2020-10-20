package kr.co.soogong.master.ui.widget

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleEdittextButtonBinding

class TitleEditTextButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleEdittextButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var hintVisible: Boolean = false
        set(value) {
            field = value
            binding.hintText.visibility = if (value) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    var hintText: String? = ""
        set(value) {
            field = value
            binding.hintText.text = value
        }

    var text: String? = ""
        set(value) {
            field = value
            binding.detail.setText(value, TextView.BufferType.EDITABLE)
        }
        get() {
            return binding.detail.text.toString()
        }

    var buttonText: String? = ""
        set(value) {
            field = value
            binding.button.text = value
        }

    var inputType: Int = InputType.TYPE_CLASS_TEXT
        set(value) {
            field = value
            binding.detail.inputType = value
        }

    fun setOnClick(listener: OnClickListener) {
        binding.button.setOnClickListener(listener)
    }

    fun addTextChangedListener(
        beforeTextChanged: (
            text: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) -> Unit = { _, _, _, _ -> },
        onTextChanged: (
            text: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) -> Unit = { _, _, _, _ -> },
        afterTextChanged: (text: Editable?) -> Unit = {}
    ) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s)
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                beforeTextChanged.invoke(text, start, count, after)
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged.invoke(text, start, before, count)
            }
        }
        binding.detail.addTextChangedListener(textWatcher)
    }
}