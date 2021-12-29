package kr.co.soogong.master.ui.major.category

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.dto.profile.CategoryDto
import kr.co.soogong.master.data.model.major.Category

class CategoryAdapter(
    private val itemClickListener: (CategoryDto) -> Unit,
) : ListAdapter<CategoryDto, CategoryViewHolder>(CategoryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder.create(parent)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }
}