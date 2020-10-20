package kr.co.soogong.master.ui.select.category

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.category.Category

@BindingAdapter("bind:category_list")
fun RecyclerView.setList(items: List<Category>?) {
    (adapter as? CategorySelectAdapter)?.submitList(items ?: emptyList())
}
