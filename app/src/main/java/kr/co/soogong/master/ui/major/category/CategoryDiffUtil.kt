package kr.co.soogong.master.ui.major.category

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.dto.profile.CategoryDto

class CategoryDiffUtil : DiffUtil.ItemCallback<CategoryDto>() {
    override fun areItemsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
        return false
    }
}