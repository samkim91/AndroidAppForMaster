@file:JvmName("AutoCompleteTextViewExt")

package kr.co.soogong.master.utility.extension;

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.databinding.BindingAdapter

// autoCompleteTextView 의 popup background 가 xml 에서 제대로 set 되지 않는 문제로 인한 binding code
@BindingAdapter("popupDrawable")
fun AppCompatAutoCompleteTextView.setPopupDrawable(drawable: Drawable) {
    this.setDropDownBackgroundDrawable(drawable)
}