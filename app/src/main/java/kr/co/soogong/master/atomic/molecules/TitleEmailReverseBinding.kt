package kr.co.soogong.master.atomic.molecules

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object TitleEmailReverseBinding {
    @JvmStatic
    @BindingAdapter("firstDetail")
    fun setTitleEmailFirstDetail(view: TitleEmail, content: String?) {
        val oldContent = view.firstDetailView.text.toString()
        if (oldContent != content) {
            view.firstDetailView.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("firstDetailAttrChanged")
    fun setTitleEmailFirstDetailInverseBindingListener(
        view: TitleEmail,
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

        view.firstDetailView.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "firstDetail", event = "firstDetailAttrChanged")
    fun getTitleEmailFirstDetail(view: TitleEmail): String {
        return view.firstDetailView.text.toString()
    }

    // 두 번째 viewtext에 대한 2-way binding
    @JvmStatic
    @BindingAdapter("secondDetail")
    fun setTitleEmailSecondDetail(view: TitleEmail, content: String?) {
        val oldContent = view.secondDetailView.text.toString()
        if (oldContent != content) {
            view.secondDetailView.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("secondDetailAttrChanged")
    fun setTitleEmailSecondDetailInverseBindingListener(
        view: TitleEmail,
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

        view.secondDetailView.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "secondDetail", event = "secondDetailAttrChanged")
    fun getTitleEmailSecondDetail(view: TitleEmail): String {
        return view.secondDetailView.text.toString()
    }
}