package kr.co.soogong.master.atomic.molecules

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object SubheadlineEmailFormReverseBinding {
    @JvmStatic
    @BindingAdapter("localPart")
    fun setSubheadlineEmailFormLocalPart(view: SubheadlineEmailForm, content: String?) {
        val oldContent = view.textInput.textInputEditText.text.toString()
        if (oldContent != content) {
            view.textInput.textInputEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("localPartAttrChanged")
    fun setSubheadlineEmailFormLocalPartInverseBindingListener(
        view: SubheadlineEmailForm,
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
    @InverseBindingAdapter(attribute = "localPart", event = "localPartAttrChanged")
    fun getSubheadlineEmailFormLocalPart(view: SubheadlineEmailForm): String {
        return view.textInput.textInputEditText.text.toString()
    }
}