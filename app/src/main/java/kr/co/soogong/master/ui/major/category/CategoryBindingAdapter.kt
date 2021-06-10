package kr.co.soogong.master.ui.major.category

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.major.Category

@BindingAdapter("bind:category_list")
fun RecyclerView.setList(items: List<Category>?) {
    (adapter as? CategoryAdapter)?.submitList(items ?: emptyList())
}
