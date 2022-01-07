package kr.co.soogong.master.ui.common.category

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.model.major.Category

class CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return false
    }
}