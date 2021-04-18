package kr.co.soogong.master.ui.widget

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewEditCountBoxBinding

class TitleEditCountBoxView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewEditCountBoxBinding.inflate(LayoutInflater.from(context), this, true)

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
            binding.title.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            binding.subTitle.text = value
        }

    var subTitleVisible: Boolean = false
        set(value) {
            field = value
            binding.subTitle.visibility = if (value) View.VISIBLE else View.INVISIBLE
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
        set(value){
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
            binding.alert.visibility = if (value) View.VISIBLE else View.INVISIBLE
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