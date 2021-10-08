package kr.co.soogong.master.ui.widget

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTitleEdittextCounterBinding

class TitleEditTextCounter @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewTitleEdittextCounterBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        initLayout()
    }

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var titleVisible: Boolean = false
        set(value) {
            field = value
            binding.title.isVisible = value
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                binding.subTitle.isVisible = true
                binding.subTitle.text = value
            }

        }

    var blackSubTitle: Boolean = false
        set(value) {
            // 기본적으로 회색이나, 필요에 따라서 검정색으로 변경
            field = value
            if (value) binding.subTitle.setTextColor(resources.getColor(R.color.text_basic_color,
                null))
            else binding.subTitle.setTextColor(resources.getColor(R.color.text_primary_color, null))
        }

    val editText: EditText
        get() = binding.text

    var text: String? = ""
        set(value) {
            field = value
            binding.text.setText(value, TextView.BufferType.EDITABLE)
        }
        get() {
            return binding.text.text.toString()
        }

    var hintText: String = ""
        set(value) {
            field = value
            binding.text.hint = value
        }

    var alertText: String? = ""
        set(value) {
            field = value
            binding.alert.text = value
        }

    var alertVisible: Boolean = false
        set(value) {
            field = value
            binding.alert.isVisible = value
        }

    var limitedCount: Int = 0
        set(value) {
            field = value
            binding.textCount.text = "0 / $value"
            binding.text.filters += InputFilter.LengthFilter(value)
        }

    private fun initLayout() {
        binding.text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = binding.text.text.toString()
                binding.textCount.text = "${text.length} / $limitedCount"
            }

            override fun afterTextChanged(s: Editable?) = Unit

        })

        binding.text.hint = hintText
        binding.text.maxEms = limitedCount + 1
    }
}