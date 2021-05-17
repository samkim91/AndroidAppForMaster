package kr.co.soogong.master.ui.image

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.estimation.ImagePath

@BindingAdapter("bind:photo_list")
fun RecyclerView.setList(items: List<ImagePath>?) {
    (adapter as? RectangleImageAdapter)?.setList(items ?: emptyList())
}

@BindingAdapter("bind:photo_list")
fun RecyclerView.setList(items: ArrayList<ImagePath>?) {
    (adapter as? RectangleImageAdapter)?.setList(items ?: emptyList())
}