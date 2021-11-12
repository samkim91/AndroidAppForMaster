package kr.co.soogong.master.ui.requirement.action.write.template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.databinding.ViewHolderEstimationTemplateItemBinding

class EstimationTemplateViewHolder(
    private val binding: ViewHolderEstimationTemplateItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        estimationTemplateDto: EstimationTemplateDto,
        buttonLeftClick: (EstimationTemplateDto) -> Unit,
        middleButtonClick: (EstimationTemplateDto) -> Unit,
        buttonRightClick: (EstimationTemplateDto) -> Unit,
    ) {
        with(binding) {
            data = estimationTemplateDto

            setLeftButtonClickListener {
                buttonLeftClick(estimationTemplateDto)
            }

            setMiddleButtonClickListener {
                middleButtonClick(estimationTemplateDto)
            }

            setRightButtonClickListener {
                buttonRightClick(estimationTemplateDto)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup) = EstimationTemplateViewHolder(
            ViewHolderEstimationTemplateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}


