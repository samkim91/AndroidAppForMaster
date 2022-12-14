package kr.co.soogong.master.presentation.atomic.molecules

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object SubheadlineTextInputReverseBinding {
    @JvmStatic
    @BindingAdapter("content")
    fun setSubheadlineTextInputContent(view: SubheadlineTextInput, content: String?) {
        val oldContent = view.textInputEditText.text.toString()
        if (oldContent != content) {
            view.textInputEditText.setText(content)
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

        view.textInputEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "content", event = "contentAttrChanged")
    fun getSubheadlineTextInputContent(view: SubheadlineTextInput): String {
        return view.textInputEditText.text.toString()
    }

    @JvmStatic
    @BindingAdapter("contentLong")
    fun setSubheadlineTextInputContent(view: SubheadlineTextInput, content: Long?) {
        val oldContent = view.textInputEditText.text.toString()
        val oldContentToLong = if (oldContent.isEmpty()) 0L else oldContent.toLong()

        if (oldContentToLong != content) {
            view.textInputEditText.setText(content.toString())
        }
    }

    @JvmStatic
    @BindingAdapter("contentLongAttrChanged")
    fun setSubheadlineTextInputLongInverseBindingListener(
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

        view.textInputEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "contentLong", event = "contentLongAttrChanged")
    fun getSubheadlineTextInputContentLong(view: SubheadlineTextInput): Long {
        return view.textInputEditText.text.toString().run {
            if (this.isNotEmpty()) this.toLong() else 0L
        }
    }
}