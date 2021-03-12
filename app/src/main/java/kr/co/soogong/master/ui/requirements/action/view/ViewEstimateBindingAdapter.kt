package kr.co.soogong.master.ui.requirements.action.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.estimation.ImagePath

@BindingAdapter("bind:photo_list")
fun RecyclerView.setList(items: List<ImagePath>?) {
    (adapter as? ViewEstimateImageAdapter)?.setList(items ?: emptyList())
}