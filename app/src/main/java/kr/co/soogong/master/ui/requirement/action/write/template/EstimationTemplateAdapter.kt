package kr.co.soogong.master.ui.requirement.action.write.template

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.dto.requirement.estimationTemplate.EstimationTemplateDto

class EstimationTemplateAdapter(
    private val buttonLeftClick: (EstimationTemplateDto) -> Unit,
    private val middleButtonClick: (EstimationTemplateDto) -> Unit,
    private val buttonRightClick: (EstimationTemplateDto) -> Unit,
) : ListAdapter<EstimationTemplateDto, EstimationTemplateViewHolder>(EstimationTemplateDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EstimationTemplateViewHolder.create(parent)

    override fun onBindViewHolder(holder: EstimationTemplateViewHolder, position: Int) {
        holder.bind(currentList[position], buttonLeftClick, middleButtonClick, buttonRightClick)
    }
}