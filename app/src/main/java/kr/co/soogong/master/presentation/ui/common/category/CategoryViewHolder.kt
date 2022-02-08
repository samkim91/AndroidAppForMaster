package kr.co.soogong.master.presentation.ui.common.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.domain.entity.common.major.Category
import kr.co.soogong.master.databinding.ViewHolderCategoryBinding

class CategoryViewHolder(
    private val binding: ViewHolderCategoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        category: Category,
        itemClickListener: (Category) -> Unit,
    ) {
        with(binding) {
            rbContent.text = category.name

            itemView.setOnClickListener {
                itemClickListener(category)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup) = CategoryViewHolder(ViewHolderCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }
}