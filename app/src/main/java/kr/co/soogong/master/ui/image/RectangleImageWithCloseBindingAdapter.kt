package kr.co.soogong.master.ui.image

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bind:photo_list")
fun RecyclerView.setList(items: List<Uri>?) {
    (adapter as? RectangleImageWithCloseAdapter)?.submitList(items ?: emptyList())
}