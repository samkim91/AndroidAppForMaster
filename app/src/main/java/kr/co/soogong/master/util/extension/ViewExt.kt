@file:JvmName("ViewExt")

package kr.co.soogong.master.util.extension

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:visible")
fun View.visible(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:visible")
fun View.visible(list: List<Any>?) {
    this.visibility = if (list.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("bind:onClick")
fun View.onClickListener(listener: View.OnClickListener){
    this.setOnClickListener(listener)
}
