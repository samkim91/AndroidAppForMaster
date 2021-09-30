package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.databinding.ViewRequirementQnaBinding

class RequirementQna @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementQnaBinding.inflate(LayoutInflater.from(context), this, true)

    var question: String? = ""
        set(value) {
            field = value
            binding.question.text = value
        }

    var answer: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                binding.answer.setText(R.string.null_text)
            } else {
                binding.answer.text = value
            }
        }

    companion object {
        fun addRequirementQna(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
            includingCancel: Boolean,
        ) {
            // Qna 추가
            requirementDto.requirementQnas?.let { list ->
                list.map {
                    RequirementQna(context).apply {
                        question = it.question
                        answer = it.answer
                    }.run {
                        container.addView(this)
                    }
                }
            }

            // description 추가
            requirementDto.description?.let {
                RequirementQna(context)
                    .apply {
                        question = context.getString(R.string.view_requirement_description)
                        answer = it
                    }.run {
                        container.addView(this)
                    }
            }

            // images 추가
            if (!requirementDto.images.isNullOrEmpty()) TitleRectangleImages(context).apply {
                question = context.getString(R.string.view_requirement_images_label)
                images = requirementDto.images
            }.run {
                container.addView(this)
            }

            // 취소하기
            if (includingCancel) RequirementDrawerContainer.addCancelButton(
                context,
                container,
                requirementDto
            )
        }
    }
}