package kr.co.soogong.master.atomic.molecules

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object SubheadlineTextInputReverseBinding {
    @JvmStatic
    @BindingAdapter("content")
    fun setSubheadlineTextInputContent(view: SubheadlineTextInput, content: String?) {
        val oldContent = view.textInput.textInputEditText.text.toString()
        if (oldContent != content) {
            view.textInput.textInputEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("contentAttrChanged")
    fun setSubheadlineTextInputInverseBindingListener(
        view: SubheadlineTextInput,
        listener: InverseBindingListener?,
    ) {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listener?.onChange()
            }
        }

        view.textInput.textInputEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "content", event = "contentAttrChanged")
    fun getSubheadlineTextInputContent(view: SubheadlineTextInput): String {
        return view.textInput.textInputEditText.text.toString()
    }
}