@file:JvmName("ReceivedBindingAdapter")

package kr.co.soogong.master.ui.requirements.received

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.ui.requirements.card.RequirementCard

@BindingAdapter("bind:received_list")
fun RecyclerView.setList(items: List<RequirementCard>?) {
    (adapter as? ReceivedAdapter)?.submitList(items ?: emptyList())
}