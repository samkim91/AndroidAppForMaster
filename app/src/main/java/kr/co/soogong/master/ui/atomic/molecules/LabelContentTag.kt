package kr.co.soogong.master.ui.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.profile.Review
import kr.co.soogong.master.data.model.requirement.estimation.EstimationPriceTypeCode
import kr.co.soogong.master.data.model.requirement.repair.CanceledReason
import kr.co.soogong.master.databinding.ViewLabelContentTagBinding
import kr.co.soogong.master.ui.profile.review.ReviewViewHolderHelper
import kr.co.soogong.master.utility.extension.dp
import kr.co.soogong.master.utility.extension.formatMoney

class LabelContentTag @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewLabelContentTagBinding.inflate(LayoutInflater.from(context), this, true)

    var label: String? = ""
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) binding.textViewLabel.text = value
        }

    var content: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                binding.textViewContent.setText(R.string.null_text)
            } else {
                binding.textViewContent.text = value
            }
        }

    var tag: String? = ""
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                binding.textViewTag.isVisible = true
                binding.textViewTag.text = value
            }
        }

    companion object {
        val params = LinearLayoutCompat.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        ).also {
            it.bottomMargin = 40.dp
        }

        fun addRequirementQnas(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
        ) {
            // Qna 추가
            requirementDto.requirementQnas?.let { qnaList ->
                qnaList.map { qna ->
                    LabelContentTag(context).also { item ->
                        item.label = qna.question
                        item.content = qna.answer
                        container.addView(item, params)
                    }
                }
            }

            // description 추가
            LabelContentTag(context).also { item ->
                item.label = context.getString(R.string.view_requirement_description)
                item.content = requirementDto.description
                container.addView(item, params)
            }

            // images 추가
            if (requirementDto.images.isNullOrEmpty()) return
            TitleRectangleImages(context).also { item ->
                item.label = context.getString(R.string.view_requirement_images_label)
                item.images = requirementDto.images
                container.addView(item, params)
            }
        }

        fun addEstimationDetail(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
        ) {
            requirementDto.estimationDto?.let { estimation ->
                // 총 견적가
                LabelContentTag(context).also { item ->
                    item.label = context.getString(R.string.estimation_total_cost)
                    item.content =
                        if (estimation.price != 0) estimation.price.formatMoney() else context.getString(
                            R.string.not_estimated_text)
                    item.tag =
                        if (estimation.price!! > 0) context.getString(if (estimation.includingVat == true) R.string.vat_included else R.string.vat_not_included) else ""
                    container.addView(item, params)
                }

                // 항목별 견적가
                if (!estimation.estimationPrices.isNullOrEmpty())
                    estimation.estimationPrices.map { price ->
                        LabelContentTag(context).also { item ->
                            item.label = when (price.priceTypeCode) {
                                EstimationPriceTypeCode.LABOR ->
                                    context.getString(R.string.estimation_labor_cost)
                                EstimationPriceTypeCode.MATERIAL ->
                                    context.getString(R.string.estimation_material_cost)
                                EstimationPriceTypeCode.TRAVEL ->
                                    context.getString(R.string.estimation_travel_cost)
                                else -> ""
                            }
                            item.content = price.partialPrice.formatMoney()
                            container.addView(item, params)
                        }
                    }

                // 제안내용
                LabelContentTag(context).also { item ->
                    item.label = context.getString(R.string.estimation_description_label)
                    item.content = estimation.description
                    container.addView(item, params)
                }

                // 실측사진
                if (estimation.images.isNullOrEmpty()) return
                TitleRectangleImages(context).also { item ->
                    item.label =
                        context.getString(R.string.view_requirement_estimation_images_label)
                    item.images = estimation.images
                    container.addView(item, params)
                }
            }
        }

        fun addDoneDetail(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
        ) {
            requirementDto.estimationDto?.let { estimation ->
                // 최종 시공가
                LabelContentTag(context).also { item ->
                    item.label = context.getString(R.string.repair_actual_price)
                    item.content = estimation.repair?.actualPrice.formatMoney()
                    item.tag =
                        if (estimation.repair?.actualPrice!! > 0) context.getString(if (estimation.repair.includingVat == true) R.string.vat_included else R.string.vat_not_included) else ""
                    container.addView(item, params)
                }
                // 제안 내용
                addEstimationDetail(context, container, requirementDto)
            }
        }


        fun addPreviousEstimation(
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
                    LabelContentTag(context).also { item ->
                        item.label = context.getString(R.string.canceled_reason_of_client)
                        item.content = preRequirement.cancelName
                        container.addView(item, params)
                    }

                    // 세부내용
                    LabelContentTag(context).also { item ->
                        item.label = context.getString(R.string.description_label)
                        item.content = preRequirement.canceledDescription
                        container.addView(item, params)
                    }

                    // 다른 마스터님의 견적가
                    LabelContentTag(context).also { item ->
                        item.label = context.getString(R.string.price_of_previous_estimation)
                        item.content = measurement.price.formatMoney()
                        item.tag =
                            if (measurement.includingVat != null) context.getString(if (measurement.includingVat == true) R.string.vat_included else R.string.vat_not_included) else ""
                        container.addView(item, params)
                    }

                    // 다른 마스터님의 코멘트
                    LabelContentTag(context).also { item ->
                        item.label = context.getString(R.string.description_of_previous_estimation)
                        item.content = measurement.description
                        container.addView(item, params)
                    }

                    // 실측자료(사진)
                    if (requirementDto.measurement.images.isNullOrEmpty()) return
                    TitleRectangleImages(context).also { item ->
                        item.label = context.getString(R.string.measure_attachment_label)
                        item.images = requirementDto.measurement.images
                        container.addView(item, params)
                    }
                }
            }
        }

        fun addCanceledReason(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
        ) {
            requirementDto.let { requirement ->
                LabelContentTag(context).also { item ->
                    item.label = context.getString(R.string.canceled_reason_label)
                    item.content =
                        CanceledReason.getCanceledReasonFromCode(requirement.canceledCode).inKorean
                    container.addView(item, params)
                }

                LabelContentTag(context).also { item ->
                    item.label = context.getString(R.string.description_label)
                    item.content = requirement.canceledDescription
                    container.addView(item, params)
                }
            }
        }

        fun addReviewDetail(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
        ) {
            requirementDto.estimationDto?.repair?.review?.let { review ->
                ReviewViewHolderHelper.create(container).also { viewHolder ->
                    viewHolder.binding(context, Review.fromReviewDto(review))
                    viewHolder.itemView.layoutParams.let { params ->
                        params as MarginLayoutParams
                        params.topMargin = 12.dp
                    }
                    container.addView(viewHolder.itemView)
                }
            }
        }
    }
}