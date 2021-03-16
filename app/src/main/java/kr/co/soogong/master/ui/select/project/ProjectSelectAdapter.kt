package kr.co.soogong.master.ui.select.project

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.domain.category.ProjectSelectDiffUtil

class ProjectSelectAdapter(
    private val listener: (Int, Project) -> Unit
) : ListAdapter<Project, ProjectSelectViewHolder>(ProjectSelectDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProjectSelectViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProjectSelectViewHolder, position: Int) {
        holder.bind(getItem(position), position, listener)
    }
}