package kr.co.soogong.master.presentation.ui.common.category

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.domain.entity.common.major.Category

class CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return false
    }
}