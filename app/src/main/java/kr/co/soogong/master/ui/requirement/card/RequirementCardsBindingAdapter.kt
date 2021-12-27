@file:JvmName("RequirementCardsBindingAdapter")

package kr.co.soogong.master.ui.requirement.card

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.requirement.RequirementCardV2
import kr.co.soogong.master.ui.home.SimpleRequirementCardAdapter

// DiffUtil.ItemCallback 을 호출하지 못하는 문제가 있어서, toMutableList 를 추가해놨다.
// 참고 : https://stackoverflow.com/questions/49726385/listadapter-not-updating-item-in-recyclerview?noredirect=1&lq=1 의 3번째 답변
@BindingAdapter("requirement_cards")
fun RecyclerView.setRequirementCards(items: List<RequirementCardV2>?) {
    (adapter as? RequirementCardsAdapter)?.submitList(items?.toMutableList() ?: emptyList())
}


@BindingAdapter("simple_requirement_cards")
fun RecyclerView.setSimpleRequirementCards(items: List<RequirementCardV2>?) {
    (adapter as? SimpleRequirementCardAdapter)?.submitList(items?.toMutableList() ?: emptyList())
}