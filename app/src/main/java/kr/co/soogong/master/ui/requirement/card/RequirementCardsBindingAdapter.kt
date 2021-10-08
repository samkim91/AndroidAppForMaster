@file:JvmName("RequirementCardsBindingAdapter")

package kr.co.soogong.master.ui.requirement.card

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.requirement.RequirementCard

@BindingAdapter("requirement_cards")
fun RecyclerView.setRequirementCards(items: List<RequirementCard>?) {
    (adapter as? RequirementCardsAdapter)?.submitList(items ?: emptyList())
}