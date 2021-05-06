package kr.co.soogong.master.ui.widget

import android.widget.CompoundButton
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import timber.log.Timber

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
        view.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            listener?.onChange()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "check", event = "checkAttrChanged")
    fun getTextCheckBoxStatus(view: TextCheckBox): Boolean {
        return view.checkBox.isChecked
    }
}