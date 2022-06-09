package kr.co.soogong.master.presentation.ui.requirement.action.visit.template

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto

class EstimationTemplateAdapter(
    private val onDeletingClicked: (EstimationTemplateDto) -> Unit,
    private val onEditingClicked: (EstimationTemplateDto) -> Unit,
    private val onApplyingClicked: (EstimationTemplateDto) -> Unit,
) : ListAdapter<EstimationTemplateDto, EstimationTemplateViewHolder>(EstimationTemplateDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EstimationTemplateViewHolder.create(parent)

    override fun onBindViewHolder(holder: EstimationTemplateViewHolder, position: Int) {
        holder.bind(currentList[position], onDeletingClicked, onEditingClicked, onApplyingClicked)
    }
}