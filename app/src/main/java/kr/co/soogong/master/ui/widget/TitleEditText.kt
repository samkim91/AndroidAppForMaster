package kr.co.soogong.master.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleEdittextBinding

class TitleEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleEdittextBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var titleVisible: Boolean = true
        set(value) {
            field = value
            binding.title.visibility = if (value) View.VISIBLE else View.GONE
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            binding.subTitle.text = value
        }

    var subTitleVisible: Boolean = false
        set(value) {
            field = value
            binding.subTitle.visibility = if (value) View.VISIBLE else View.GONE
        }

    var alertVisible: Boolean = false
        set(value) {
            field = value
            binding.alert.visibility = if (value) View.VISIBLE else View.GONE
        }

    var alertText: String? = ""
        set(value) {
            field = value
            binding.alert.text = value
        }

    val editText: EditText
        get() = binding.detail

    var text: String? = ""
        set(value) {
            field = value
            binding.detail.setText(value, TextView.BufferType.EDITABLE)
        }
        get() {
            return binding.detail.text.toString()
        }

    var hintText: String? = ""
        set(value) {
            field = value
            binding.detail.hint = value
        }

    var setInputType: Int = InputType.TYPE_CLASS_TEXT
        set(value) {
            field = value
            binding.detail.inputType = value
        }

    var setMaxLength: Int = 0
        set(value) {
            field = value
            binding.detail.filters += InputFilter.LengthFilter(value)
        }


    fun setEditTextBackground(drawable: Drawable?) {
        binding.detail.background = drawable
    }

    private lateinit var textWatcher: TextWatcher

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
        textWatcher = object : TextWatcher {
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

    fun addFocusChangeListener(
        onFocusChange: (
            view: View?,
            hasFocus: Boolean
        ) -> Unit
    ) {
        val focusListener =
            OnFocusChangeListener { v, hasFocus -> onFocusChange.invoke(v, hasFocus) }
        binding.detail.onFocusChangeListener = focusListener
    }
}