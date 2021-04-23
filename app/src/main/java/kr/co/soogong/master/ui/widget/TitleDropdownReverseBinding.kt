package kr.co.soogong.master.ui.widget

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object TitleDropdownReverseBinding {
    // todo.. viewText에는 2way binding을 할 필요가 없음... 1way로 추후 변경 필요
    @JvmStatic
    @BindingAdapter("content")
    fun setTitleEditCountBoxContent(view: TitleDropdown, content: String?) {
        val oldContent = view.textView.text.toString()
        if(oldContent != content){
            view.textView.text = content
        }
    }

    @JvmStatic
    @BindingAdapter("contentAttrChanged")
    fun setTitleEditCountBoxInverseBindingListener(view: TitleDropdown, listener: InverseBindingListener?){
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listener?.onChange()
            }
        }

        view.textView.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "content", event = "contentAttrChanged")
    fun getContent(view: TitleDropdown): String {
        return view.textView.text.toString()
    }
}