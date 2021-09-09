package kr.co.soogong.master.ui.widget

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object SearchBarReverseBinding {
    @JvmStatic
    @BindingAdapter("searchText")
    fun setTitleEditTextButtonFirstContent(view: SearchBar, content: String?) {
        val oldContent = view.searchEditText.text.toString()
        if(oldContent != content){
            view.searchEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("searchTextAttrChanged")
    fun setTitleEditTextButtonFirstContentInverseBindingListener(view: SearchBar, listener: InverseBindingListener?){
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listener?.onChange()
            }
        }

        view.searchEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "searchText", event = "searchTextAttrChanged")
    fun getFirstContent(view: SearchBar): String {
        return view.searchEditText.text.toString()
    }
}