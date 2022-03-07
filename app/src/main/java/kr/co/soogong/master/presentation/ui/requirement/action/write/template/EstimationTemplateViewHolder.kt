package kr.co.soogong.master.presentation.ui.requirement.action.write.template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationTemplateDto
import kr.co.soogong.master.databinding.ViewHolderEstimationTemplateItemBinding

class EstimationTemplateViewHolder(
    private val binding: ViewHolderEstimationTemplateItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        estimationTemplateDto: EstimationTemplateDto,
        onDeletingClicked: (EstimationTemplateDto) -> Unit,
        onEditingClicked: (EstimationTemplateDto) -> Unit,
        onApplyingClicked: (EstimationTemplateDto) -> Unit,
    ) {
        with(binding) {
            data = estimationTemplateDto

            setDeleteClickListener {
                onDeletingClicked(estimationTemplateDto)
            }

            setEditingClickListener {
                onEditingClicked(estimationTemplateDto)
            }

            setApplyingClickListener {
                onApplyingClicked(estimationTemplateDto)
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


