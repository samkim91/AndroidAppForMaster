package kr.co.soogong.master.ui.select.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.databinding.ViewholderCategoryBinding

class CategorySelectViewHolder(
    private val binding: ViewholderCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        category: Category,
        clickListener: (Category) -> Unit
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
        fun create(parent: ViewGroup): CategorySelectViewHolder {
            val binding = ViewholderCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CategorySelectViewHolder(binding)
        }
    }
}