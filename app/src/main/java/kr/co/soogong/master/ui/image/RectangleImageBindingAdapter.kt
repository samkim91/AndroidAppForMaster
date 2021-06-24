package kr.co.soogong.master.ui.image

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.AttachmentDto


@BindingAdapter("bind:photo_list")
fun RecyclerView.setList(items: MutableList<AttachmentDto>?) {
    (adapter as? RectangleImageAdapter)?.setList(items ?: emptyList())
}