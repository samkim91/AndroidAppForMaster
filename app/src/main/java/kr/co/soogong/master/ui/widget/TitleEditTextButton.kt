package kr.co.soogong.master.ui.widget

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleEdittextButtonBinding

class TitleEditTextButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleEdittextButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
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

    var hintVisible: Boolean = false
        set(value) {
            field = value
            binding.hintText.visibility = if (value) View.VISIBLE else View.GONE
        }

    var hintText: String? = ""
        set(value) {
            field = value
            binding.hintText.text = value
        }

    val firstEditText: EditText
        get() = binding.firstEditText

    var setFirstEditTextHint: String? = ""
        set(value) {
            field = value
            binding.firstEditText.hint = value
        }

    val secondEditText: EditText
        get() = binding.secondEditText

    var setSecondEditTextHint: String? = ""
        set(value) {
            field = value
            binding.secondEditText.hint = value
        }

    var setSecondEditTextVisible: Boolean = false
        set(value) {
            field = value
            binding.secondEditText.visibility = if (value) View.VISIBLE else View.GONE
        }

    var setFirstEditTextEnable: Boolean = true
        set(value){
            field = value
            binding.firstEditText.isEnabled = false
        }

    var setSecondEditTextEnable: Boolean = true
        set(value){
            field = value
            binding.secondEditText.isEnabled = false
        }

    var inputTypeFirst: Int = InputType.TYPE_CLASS_TEXT
        set(value) {
            field = value
            binding.firstEditText.inputType = value
        }

    var inputTypeSecond: Int = InputType.TYPE_CLASS_TEXT
        set(value) {
            field = value
            binding.secondEditText.inputType = value
        }

    var buttonText: String? = ""
        set(value) {
            field = value
            binding.button.text = value
        }

    fun setOnClick(listener: OnClickListener) {
        binding.button.setOnClickListener(listener)
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
}