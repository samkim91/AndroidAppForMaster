package kr.co.soogong.master.atomic.molecules

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object SubheadlineTextareaCounterReverseBinding {
    @JvmStatic
    @BindingAdapter("content")
    fun setTitleEditTextCounterContent(view: SubheadlineTextareaCounter, content: String?) {
        val oldContent = view.textareaCounter.textInputEditText.text.toString()
        if (oldContent != content) {
            view.textareaCounter.textInputEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("contentAttrChanged")
    fun setTitleEditTextCounterInverseBindingListener(
        view: SubheadlineTextareaCounter,
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

        view.textareaCounter.textInputEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "content", event = "contentAttrChanged")
    fun getContent(view: SubheadlineTextareaCounter): String {
        return view.textareaCounter.textInputEditText.text.toString()
    }
}