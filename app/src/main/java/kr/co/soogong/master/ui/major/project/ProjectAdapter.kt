package kr.co.soogong.master.ui.major.project

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.dto.profile.ProjectDto

class ProjectAdapter(
    private val itemClickListener: (project: ProjectDto, isChecked: Boolean) -> Unit,
) : ListAdapter<ProjectDto, ProjectViewHolder>(ProjectDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProjectViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }
}