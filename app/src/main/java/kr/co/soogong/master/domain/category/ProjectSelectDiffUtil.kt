package kr.co.soogong.master.domain.category

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.category.Project

class ProjectSelectDiffUtil : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return false
    }
}