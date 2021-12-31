package kr.co.soogong.master.atomic.molecules

import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object HeadlineButtonContentReverseBinding {
    @JvmStatic
    @BindingAdapter("contentEditable")
    fun setHeadlineButtonContentContent(view: HeadlineButtonContent, content: String?) {
        val oldContent = view.tvContent.text.toString()
        if (oldContent != content) {
            view.tvContent.setText(content)
            view.tvContent.isVisible = !content.isNullOrEmpty()
            view.setButtonStatus()
        }
    }

    @JvmStatic
    @BindingAdapter("contentEditableAttrChanged")
    fun setHeadlineButtonContentInverseBindingListener(
        view: HeadlineButtonContent,
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

        view.tvContent.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "contentEditable", event = "contentEditableAttrChanged")
    fun getHeadlineButtonContentContent(view: HeadlineButtonContent): String {
        return view.tvContent.text.toString()
    }
}