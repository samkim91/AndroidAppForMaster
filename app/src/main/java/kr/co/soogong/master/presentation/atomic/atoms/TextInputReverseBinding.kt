package kr.co.soogong.master.presentation.atomic.atoms

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object TextInputReverseBinding {
    @JvmStatic
    @BindingAdapter("content")
    fun setTextInputContent(view: TextInput, content: String?) {
        val oldContent = view.textInputEditText.text.toString()
        if (oldContent != content) {
            view.textInputEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("contentAttrChanged")
    fun setTextInputInverseBindingListener(
        view: TextInput,
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

        view.textInputEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "content", event = "contentAttrChanged")
    fun getTextInputContent(view: TextInput): String {
        return view.textInputEditText.text.toString()
    }
}