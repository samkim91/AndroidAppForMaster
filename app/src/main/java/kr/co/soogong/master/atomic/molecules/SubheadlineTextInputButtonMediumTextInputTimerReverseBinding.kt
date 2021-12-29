package kr.co.soogong.master.atomic.molecules

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import kr.co.soogong.master.atomic.organisms.SubheadlineTextInputButtonMediumTextInputTimer

object SubheadlineTextInputButtonMediumTextInputTimerReverseBinding {
    @JvmStatic
    @BindingAdapter("firstContent")
    fun setSubheadlineTextInputButtonMediumTextInputTimerFirstContent(view: SubheadlineTextInputButtonMediumTextInputTimer, content: String?) {
        val oldContent = view.textInputButtonMedium.textInput.textInputEditText.text.toString()
        if (oldContent != content) {
            view.textInputButtonMedium.textInput.textInputEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("firstContentAttrChanged")
    fun setSubheadlineTextInputButtonMediumTextInputTimerFirstInverseBindingListener(
        view: SubheadlineTextInputButtonMediumTextInputTimer,
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

        view.textInputButtonMedium.textInput.textInputEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "firstContent", event = "firstContentAttrChanged")
    fun getSubheadlineTextInputButtonMediumTextInputTimerFirstContent(view: SubheadlineTextInputButtonMediumTextInputTimer): String {
        return view.textInputButtonMedium.textInput.textInputEditText.text.toString()
    }

    @JvmStatic
    @BindingAdapter("secondContent")
    fun setSubheadlineTextInputButtonMediumTextInputTimerSecondContent(view: SubheadlineTextInputButtonMediumTextInputTimer, content: String?) {
        val oldContent = view.textInputTimer.textInputEditText.text.toString()
        if (oldContent != content) {
            view.textInputTimer.textInputEditText.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("secondContentAttrChanged")
    fun setSubheadlineTextInputButtonMediumTextInputTimerSecondInverseBindingListener(
        view: SubheadlineTextInputButtonMediumTextInputTimer,
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

        view.textInputTimer.textInputEditText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "secondContent", event = "secondContentAttrChanged")
    fun getSubheadlineTextInputButtonMediumTextInputTimerSecondContent(view: SubheadlineTextInputButtonMediumTextInputTimer): String {
        return view.textInputTimer.textInputEditText.text.toString()
    }
}