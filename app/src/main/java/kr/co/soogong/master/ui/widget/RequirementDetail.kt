package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.databinding.ViewRequirementQnaBinding
import kr.co.soogong.master.utility.extension.dp
import kr.co.soogong.master.utility.extension.formatMoney

class RequirementDetail @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementQnaBinding.inflate(LayoutInflater.from(context), this, true)

    var key: String? = ""
        set(value) {
            field = value
            binding.key.text = value
        }

    var value: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                binding.value.setText(R.string.null_text)
            } else {
                binding.value.text = value
            }
        }

    var extra: String? = ""
        set(value) {
            if (!value.isNullOrEmpty()) {
                field = value
                binding.extra.text = value
                binding.extra.isVisible = true
            }
        }

    companion object {
        fun addRequirementDetail(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
            includingCancel: Boolean,
        ) {
            // Qna 추가
            requirementDto.requirementQnas?.let { list ->
                list.map {
                    RequirementDetail(context).apply {
                        key = it.question
                        value = it.answer
                    }.run {
                        container.addView(this)
                    }
                }
            }

            // description 추가
            requirementDto.description?.let {
                RequirementDetail(context).apply {
                    key = context.getString(R.string.view_requirement_description)
                    value = it
                }.run {
                    container.addView(this)
                }
            }

            // images 추가
            if (!requirementDto.images.isNullOrEmpty())
                TitleRectangleImages(context).apply {
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

        fun addPreviousEstimationDetail(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
        ) {
            requirementDto.measurement?.let { measurement ->
                requirementDto.previousRequirementDto?.let { preRequirement ->
                    // 부가 정보 부제목
                    AppCompatTextView(context).apply {
                        text = context.getString(R.string.subtitle_of_previous_estimation)
                        setTextColor(resources.getColor(R.color.c_22D47B, null))
                        setTextAppearance(R.style.text_style_12sp_bold)
                    }.run {
                        LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT)
                            .apply { setPadding(0.dp, 0.dp, 0.dp, 12.dp) }
                            .let { container.addView(this, it) }
                    }

                    // 추가 요청 사유
                    RequirementDetail(context).apply {
                        key = context.getString(R.string.canceled_reason_of_client)
                        value = preRequirement.cancelName
                    }.run {
                        container.addView(this)
                    }

                    // 세부내용
                    preRequirement.canceledDescription?.let {
                        RequirementDetail(context).apply {
                            key = context.getString(R.string.description_label)
                            value = it
                        }.run {
                            container.addView(this)
                        }
                    }

                    // 다른 마스터님의 견적가
                    RequirementDetail(context).apply {
                        key = context.getString(R.string.price_of_previous_estimation)
                        value = measurement.price.formatMoney()
                        extra =
                            if (measurement.includingVat != null) context.getString(if (measurement.includingVat == true) R.string.vat_included else R.string.vat_not_included) else ""
                    }.run {
                        container.addView(this)
                    }

                    // 다른 마스터님의 코멘트
                    RequirementDetail(context).apply {
                        key = context.getString(R.string.description_of_previous_estimation)
                        value = measurement.description
                    }.run {
                        container.addView(this)
                    }

                    // 실측자료
                    if (!requirementDto.measurement.images.isNullOrEmpty())
                        TitleRectangleImages(context).apply {
                            question = context.getString(R.string.measure_attachment_label)
                            images = requirementDto.measurement.images
                        }.run {
                            container.addView(this)
                        }
                }
            }
        }
    }
}