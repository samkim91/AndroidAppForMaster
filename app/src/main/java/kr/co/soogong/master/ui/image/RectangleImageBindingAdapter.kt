package kr.co.soogong.master.ui.image

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("imageUrls")
fun RecyclerView.setImageUrls(items: List<String>) {
    if (items.isNotEmpty()) (adapter as? RectangleImageAdapter)?.setImageUrls(items)
}