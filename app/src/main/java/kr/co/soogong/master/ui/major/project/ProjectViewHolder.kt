package kr.co.soogong.master.ui.major.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.profile.ProjectDto
import kr.co.soogong.master.databinding.ViewHolderProjectBinding
import kr.co.soogong.master.utility.extension.dp

class ProjectViewHolder(
    private val binding: ViewHolderProjectBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        project: ProjectDto,
        itemClickListener: (project: ProjectDto, isChecked: Boolean) -> Unit,
    ) {
        binding.run {
            cbContent.text = project.name

            itemView.layoutParams =
                (itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    marginStart = 15.dp
                    marginEnd = 15.dp
                    bottomMargin = 10.dp
                    topMargin = 10.dp
                }

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