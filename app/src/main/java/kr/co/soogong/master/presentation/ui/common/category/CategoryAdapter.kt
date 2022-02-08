package kr.co.soogong.master.presentation.ui.common.category

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.domain.entity.common.major.Category

class CategoryAdapter(
    private val itemClickListener: (Category) -> Unit,
) : ListAdapter<Category, CategoryViewHolder>(CategoryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder.create(parent)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }
}