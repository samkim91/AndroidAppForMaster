package kr.co.soogong.master.ui.select.category

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.category.Category

class CategorySelectAdapter(
    private val list: ArrayList<Category> = arrayListOf(),
    private val clickListener: (Category) -> Unit
) : RecyclerView.Adapter<CategorySelectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategorySelectViewHolder.create(parent)

    override fun onBindViewHolder(holder: CategorySelectViewHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<Category>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}