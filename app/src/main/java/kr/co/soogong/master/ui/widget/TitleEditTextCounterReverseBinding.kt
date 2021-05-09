package kr.co.soogong.master.ui.widget

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object TitleEditTextCounterReverseBinding {
    @JvmStatic
    @BindingAdapter("content")
    fun setTitleEditTextCounterContent(view: TitleEditTextCounterView, content: String?) {
        val oldContent = view.editText.text.toString()
        if(oldContent != content){
            view.editText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("contentAttrChanged")
    fun setTitleEditTextCounterInverseBindingListener(view: TitleEditTextCounterView, listener: InverseBindingListener?){
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listener?.onChange()
            }
        }

        view.editText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "content", event = "contentAttrChanged")
    fun getContent(view: TitleEditTextCounterView): String {
        return view.editText.text.toString()
    }
}