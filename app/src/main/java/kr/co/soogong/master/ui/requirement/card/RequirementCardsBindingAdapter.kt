@file:JvmName("RequirementCardsBindingAdapter")

package kr.co.soogong.master.ui.requirement.card

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.home.SimpleRequirementCardAdapter

@BindingAdapter("requirement_cards")
fun RecyclerView.setRequirementCards(items: List<RequirementCard>?) {
    (adapter as? RequirementCardsAdapter)?.submitList(items ?: emptyList())
}

@BindingAdapter("simple_requirement_cards")
fun RecyclerView.setSimpleRequirementCards(items: List<RequirementCard>?) {
    (adapter as? SimpleRequirementCardAdapter)?.submitList(items ?: emptyList())
}