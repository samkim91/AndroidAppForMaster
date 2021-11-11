package kr.co.soogong.master.ui.major.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.databinding.ViewHolderCategoryBinding

class CategoryViewHolder(
    private val binding: ViewHolderCategoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        category: Category,
        clickListener: (Category) -> Unit,
    ) {
        binding.run {
            text.text = category.name

            root.setOnClickListener {
                clickListener(category)
            }
        }

        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): CategoryViewHolder {
            val binding = ViewHolderCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CategoryViewHolder(binding)
        }
    }
}