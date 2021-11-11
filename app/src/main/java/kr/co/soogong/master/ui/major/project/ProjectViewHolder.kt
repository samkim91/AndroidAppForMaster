package kr.co.soogong.master.ui.major.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.Project
import kr.co.soogong.master.databinding.ViewHolderProjectBinding

class ProjectViewHolder(
    private val binding: ViewHolderProjectBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        project: Project,
        position: Int,
        clickListener: (Int, Project) -> Unit,
    ) {
        binding.run {
            text.text = project.name

            if (project.checked) check.setImageResource(R.drawable.ic_active_check_box) else check.setImageResource(
                R.drawable.ic_inactive_check_box)

            root.setOnClickListener {
                clickListener(position, project)
            }
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): ProjectViewHolder {
            val binding = ViewHolderProjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ProjectViewHolder(binding)
        }
    }
}