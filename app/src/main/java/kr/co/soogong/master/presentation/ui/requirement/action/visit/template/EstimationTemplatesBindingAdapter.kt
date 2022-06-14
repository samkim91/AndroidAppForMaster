@file:JvmName("EstimationTemplatesBindingAdapter")

package kr.co.soogong.master.presentation.ui.requirement.action.visit.template

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto

@BindingAdapter("estimation_templates")
fun RecyclerView.setEstimationTemplates(items: List<EstimationTemplateDto>?) {
    (adapter as? EstimationTemplateAdapter)?.submitList(items ?: emptyList())
}