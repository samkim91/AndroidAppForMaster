package kr.co.soogong.master.ui.major.project

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.dto.profile.ProjectDto

class ProjectDiffUtil : DiffUtil.ItemCallback<ProjectDto>() {
    override fun areItemsTheSame(oldItem: ProjectDto, newItem: ProjectDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProjectDto, newItem: ProjectDto): Boolean {
        return false
    }
}