package kr.co.soogong.master.presentation.ui.common.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.databinding.ViewHolderProjectBinding

class ProjectViewHolder(
    private val binding: ViewHolderProjectBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        project: Project,
        itemClickListener: (project: Project, isChecked: Boolean) -> Unit,
    ) {
        binding.run {
            cbContent.text = project.name

            itemView.setOnClickListener {
                itemClickListener(project, cbContent.isChecked)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup) = ProjectViewHolder(ViewHolderProjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }
}