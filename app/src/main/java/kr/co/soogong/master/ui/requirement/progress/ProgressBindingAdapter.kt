@file:JvmName("ProgressBindingAdapter")

package kr.co.soogong.master.ui.requirement.progress

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.requirement.RequirementCard

@BindingAdapter("bind:progress_list")
fun RecyclerView.setList(items: List<RequirementCard>?) {
    (adapter as? ProgressAdapter)?.submitList(items ?: emptyList())
}
