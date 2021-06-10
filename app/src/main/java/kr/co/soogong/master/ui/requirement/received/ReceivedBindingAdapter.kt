@file:JvmName("ReceivedBindingAdapter")

package kr.co.soogong.master.ui.requirement.received

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.requirement.RequirementCard

@BindingAdapter("bind:received_list")
fun RecyclerView.setList(items: List<RequirementCard>?) {
    (adapter as? ReceivedAdapter)?.submitList(items ?: emptyList())
}