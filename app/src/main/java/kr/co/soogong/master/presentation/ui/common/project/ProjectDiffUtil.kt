package kr.co.soogong.master.presentation.ui.common.project

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.domain.entity.common.major.Project

class ProjectDiffUtil : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return false
    }
}