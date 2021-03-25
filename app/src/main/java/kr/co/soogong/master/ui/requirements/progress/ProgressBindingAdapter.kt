@file:JvmName("ProgressBindingAdapter")

package kr.co.soogong.master.ui.requirements.progress

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.domain.requirements.RequirementCard

@BindingAdapter("bind:progress_list")
fun RecyclerView.setList(items: List<RequirementCard>?) {
    (adapter as? ProgressAdapter)?.submitList(items ?: emptyList())
}
