package kr.co.soogong.master.ui.select.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.databinding.ViewholderProjectBinding

class ProjectSelectViewHolder(
    private val binding: ViewholderProjectBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        project: Project,
        position: Int,
        clickListener: (Int, Project) -> Unit
    ) {
        binding.run {
            text.text = project.name

            check.visibility = if (project.checked) {
                View.VISIBLE
            } else {
                View.GONE
            }

            root.setOnClickListener {
                clickListener(position, project)
            }
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): ProjectSelectViewHolder {
            val binding = ViewholderProjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ProjectSelectViewHolder(binding)
        }
    }
}