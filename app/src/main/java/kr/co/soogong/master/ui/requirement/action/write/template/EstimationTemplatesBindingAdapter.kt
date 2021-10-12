@file:JvmName("EstimationTemplatesBindingAdapter")

package kr.co.soogong.master.ui.requirement.action.write.template

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.requirement.estimationTemplate.EstimationTemplateDto

@BindingAdapter("estimation_templates")
fun RecyclerView.setEstimationTemplates(items: List<EstimationTemplateDto>?) {
    (adapter as? EstimationTemplateAdapter)?.submitList(items ?: emptyList())
}