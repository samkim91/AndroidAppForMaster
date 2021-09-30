@file:JvmName("ReceivedBindingAdapter")

package kr.co.soogong.master.ui.requirement.received

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.requirement.card.RequirementCardsAdapter

@BindingAdapter("requirement_cards")
fun RecyclerView.setRequirementCards(items: List<RequirementCard>?) {
    (adapter as? RequirementCardsAdapter)?.submitList(items ?: emptyList())
}