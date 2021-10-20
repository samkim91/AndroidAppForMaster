package kr.co.soogong.master.ui.widget

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object TextCheckBoxReverseBinding {
    @JvmStatic
    @BindingAdapter("check")
    fun setTextCheckBoxStatus(view: TextCheckBox, check: Boolean?) {
        val oldStatus = view.checkBox.isChecked
        if (oldStatus != check) {
            view.checkBox.isChecked = check ?: false
        }
    }

    @JvmStatic
    @BindingAdapter("checkAttrChanged")
    fun setTextCheckBoxInverseBindingListener(
        view: TextCheckBox,
        listener: InverseBindingListener?,
    ) {
        view.checkBox.setOnCheckedChangeListener { _, _ ->
            listener?.onChange()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "check", event = "checkAttrChanged")
    fun getTextCheckBoxStatus(view: TextCheckBox): Boolean {
        return view.checkBox.isChecked
    }
}