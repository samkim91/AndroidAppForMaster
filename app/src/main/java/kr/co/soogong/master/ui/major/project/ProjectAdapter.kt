package kr.co.soogong.master.ui.major.project

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.major.Project

class ProjectAdapter(
    private val listener: (Int, Project) -> Unit
) : ListAdapter<Project, ProjectViewHolder>(ProjectDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProjectViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position), position, listener)
    }
}