@file:JvmName("ViewExt")

package kr.co.soogong.master.utility.extension

import android.view.View
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.data.model.profile.Review

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