@file:JvmName("ViewExt")

package kr.co.soogong.master.utility.extension

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:visible")
fun View.visible(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:visible")
fun View.visible(list: List<Any>?) {
    this.visibility = if (list.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("bind:show")
fun View.invisible(list: List<Any>?) {
    this.visibility = if (list.isNullOrEmpty()) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:onClick")
fun View.onClickListener(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

// whenFocused
// 이 view 가 ViewGroup 일 경우 true 로 set. 이유는 레이아웃이 focus 를 가졌을 때, 키보드를 숨김.
// 이 view 가 Edittext 일 경우 false 로 set. 이유는 edittext 가 focus 를 잃었을 때, 키보드를 숨김.
@BindingAdapter("hideKeyboardWhenFocused")
fun View.hideKeyboardFocusChanged(whenFocused: Boolean) {
    this.setOnFocusChangeListener { view, hasFocus ->
        when (whenFocused) {
            true -> if (hasFocus) view.hideKeyboard()
            false -> if (!hasFocus) view.hideKeyboard()
        }
    }
}

fun View.hideKeyboard() =
    (this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .also { inputMethodManager ->
            inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
        }
