@file:JvmName("DoneBindingAdapter")

package kr.co.soogong.master.ui.requirements.done

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.domain.requirements.RequirementCard

@BindingAdapter("bind:done_list")
fun RecyclerView.setList(items: List<RequirementCard>?) {
    (adapter as? DoneAdapter)?.submitList(items ?: emptyList())
}