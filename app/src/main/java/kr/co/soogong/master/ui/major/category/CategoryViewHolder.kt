package kr.co.soogong.master.ui.major.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.profile.CategoryDto
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.databinding.ViewHolderCategoryBinding
import kr.co.soogong.master.utility.extension.dp

class CategoryViewHolder(
    private val binding: ViewHolderCategoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        category: CategoryDto,
        itemClickListener: (CategoryDto) -> Unit,
    ) {
        with(binding) {
            rbContent.text = category.name

            itemView.layoutParams =
                (itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    marginStart = 15.dp
                    marginEnd = 15.dp
                    bottomMargin = 10.dp
                    topMargin = 10.dp
                }

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