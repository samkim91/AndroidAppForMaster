@file:JvmName("RecyclerViewExt")

package kr.co.soogong.master.utility.extension;

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("setDivider")
fun RecyclerView.setDivider(drawable: Drawable) {
    val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
    dividerItemDecoration.setDrawable(drawable)

    this.addItemDecoration(dividerItemDecoration)
}

