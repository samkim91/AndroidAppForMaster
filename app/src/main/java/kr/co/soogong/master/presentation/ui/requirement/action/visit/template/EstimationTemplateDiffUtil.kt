package kr.co.soogong.master.presentation.ui.requirement.action.visit.template

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto

class EstimationTemplateDiffUtil : DiffUtil.ItemCallback<EstimationTemplateDto>() {
    override fun areItemsTheSame(
        oldItem: EstimationTemplateDto,
        newItem: EstimationTemplateDto,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: EstimationTemplateDto,
        newItem: EstimationTemplateDto,
    ): Boolean {
        return oldItem == newItem
    }
}