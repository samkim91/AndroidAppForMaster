package kr.co.soogong.master.presentation.ui.common.project

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.domain.entity.common.major.Project

class ProjectAdapter(
    private val itemClickListener: (project: Project, checkBox: AppCompatCheckBox) -> Unit,
) : ListAdapter<Project, ProjectViewHolder>(ProjectDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProjectViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }
}