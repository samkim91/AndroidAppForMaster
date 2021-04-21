package kr.co.soogong.master.ui.widget

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object TitleEditTextButtonReverseBinding {
    // 첫 번째 edittext에 대한 2-way binding
    @JvmStatic
    @BindingAdapter("firstContent")
    fun setTitleEditTextButtonFirstContent(view: TitleEditTextButton, content: String?) {
        val oldContent = view.firstEditText.text.toString()
        if(oldContent != content){
            view.firstEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("firstContentAttrChanged")
    fun setTitleEditTextButtonFirstContentInverseBindingListener(view: TitleEditTextButton, listener: InverseBindingListener?){
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listener?.onChange()
            }
        }

        view.firstEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "firstContent", event = "firstContentAttrChanged")
    fun getFirstContent(view: TitleEditTextButton): String {
        return view.firstEditText.text.toString()
    }

    // 두 번째 edittext에 대한 2-way binding
    @JvmStatic
    @BindingAdapter("secondContent")
    fun setTitleEditTextButtonSecondContent(view: TitleEditTextButton, content: String?) {
        val oldContent = view.secondEditText.text.toString()
        if(oldContent != content){
            view.secondEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("secondContentAttrChanged")
    fun setTitleEditTextButtonSecondContentInverseBindingListener(view: TitleEditTextButton, listener: InverseBindingListener?){
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listener?.onChange()
            }
        }

        view.secondEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "secondContent", event = "secondContentAttrChanged")
    fun getSecondContent(view: TitleEditTextButton): String {
        return view.secondEditText.text.toString()
    }
}