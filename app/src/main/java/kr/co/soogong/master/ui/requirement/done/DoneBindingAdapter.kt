@file:JvmName("DoneBindingAdapter")

package kr.co.soogong.master.ui.requirement.done

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.requirement.RequirementCard

@BindingAdapter("bind:done_list")
fun RecyclerView.setList(items: List<RequirementCard>?) {
    (adapter as? DoneAdapter)?.submitList(items ?: emptyList())
}