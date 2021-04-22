package kr.co.soogong.master.ui.widget

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object TitleSettingTimeReverseBinding {
    // todo.. viewText에는 2way binding을 할 필요가 없음... 1way로 추후 변경 필요
    // 첫 번째 viewtext에 대한 2-way binding..
    @JvmStatic
    @BindingAdapter("startTime")
    fun setTitleSettingTimeFirstContent(view: TitleSettingTime, content: String?) {
        val oldContent = view.starTime.text.toString()
        if(oldContent != content){
            view.starTime.text = content
        }
    }

    @JvmStatic
    @BindingAdapter("startTimeAttrChanged")
    fun setTitleSettingTimeFirstInverseBindingListener(view: TitleSettingTime, listener: InverseBindingListener?){
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listener?.onChange()
            }
        }

        view.starTime.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "startTime", event = "startTimeAttrChanged")
    fun getFirstContent(view: TitleSettingTime): String {
        return view.starTime.text.toString()
    }

    // 두 번째 viewtext에 대한 2-way binding
    @JvmStatic
    @BindingAdapter("endTime")
    fun setTitleSettingTimeSecondContent(view: TitleSettingTime, content: String?) {
        val oldContent = view.endTime.text.toString()
        if(oldContent != content){
            view.endTime.text = content
        }
    }

    @JvmStatic
    @BindingAdapter("endTimeAttrChanged")
    fun setTitleSettingTimeSecondInverseBindingListener(view: TitleSettingTime, listener: InverseBindingListener?){
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listener?.onChange()
            }
        }

        view.endTime.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "endTime", event = "startTimeAttrChanged")
    fun getSecondContent(view: TitleSettingTime): String {
        return view.endTime.text.toString()
    }
}