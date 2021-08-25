package kr.co.soogong.master.ui.widget

import android.content.Context
import android.graphics.Typeface
import android.icu.text.DecimalFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.model.requirement.estimation.EstimationPriceTypeCode
import kr.co.soogong.master.databinding.ViewEstimationDetailBinding
import kr.co.soogong.master.utility.extension.dp

class EstimationDetail @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewEstimationDetailBinding.inflate(LayoutInflater.from(context), this, true)

    var key: String? = ""
        set(value) {
            field = value
            binding.key.text = value
        }

    var value: String? = ""
        set(value) {
            if(!value.isNullOrEmpty()) {
                field = value
                binding.value.text = value
            }
        }

    var image: String? = ""
        set(value) {
            if(!value.isNullOrEmpty()) {
                field = value
                binding.cardView.isVisible = true
                binding.value.isVisible = false
                binding.imageUrl = value
            }
        }

    var bold: Boolean? = false
        set(value) {
            field = value
            binding.key.setTypeface(binding.key.typeface, Typeface.BOLD)
            binding.value.setTypeface(binding.value.typeface, Typeface.BOLD)
        }

    companion object {
        fun addEstimationDetail(
            context: Context,
            container: ViewGroup,
            requirementDto: RequirementDto,
            includingCancel: Boolean,
        ) {
            requirementDto.estimationDto?.let { estimation ->
                // 최종 시공가
                if (estimation.repair?.actualPrice != null) {
                    EstimationDetail(context).apply {
                        key = context.getString(R.string.repair_actual_price)
                        value = "${DecimalFormat("#,###").format(estimation.repair.actualPrice)}원"
                        bold = true
                    }.run {
                        container.addView(this)
                    }
                }

                // 총 견적가
                if (estimation.price != null) {
                    EstimationDetail(context).apply {
                        key = context.getString(R.string.estimation_total_cost)
                        value = if (estimation.price != 0) {
                            "${DecimalFormat("#,###").format(estimation.price)}원"
                        } else {
                            context.getString(R.string.not_estimated_text)
                        }
                    }.run {
                        container.addView(this)
                    }
                }

                // 항목별 견적가
                if (!estimation.estimationPrices.isNullOrEmpty()) {
                    estimation.estimationPrices.map {
                        EstimationDetail(context).apply {
                            key = when (it.priceTypeCode) {
                                EstimationPriceTypeCode.LABOR ->
                                    context.getString(R.string.estimation_labor_cost)
                                EstimationPriceTypeCode.MATERIAL ->
                                    context.getString(R.string.estimation_material_cost)
                                EstimationPriceTypeCode.TRAVEL ->
                                    context.getString(R.string.estimation_travel_cost)
                                else -> ""
                            }
                            value = "${DecimalFormat("#,###").format(it.partialPrice)}원"
                        }.run {
                            container.addView(this)
                        }
                    }
                }

                // 제안내용
                if (estimation.description != null) {
                    EstimationDetail(context).apply {
                        key = context.getString(R.string.estimation_description_label)
                        value = estimation.description
                    }.run {
                        container.addView(this)
                    }
                }

                // TODO: 2021/08/24 estimation 에 실측정보가 온다면 이것을 그려줘야한다.
//                if (estimation.measureAttachment != null) {
//                    EstimationDetail(context).apply {
//                        key = context.getString(R.string.estimation_total_cost)
//                        image = estimation.measureAttachment
//                    }.run {
//                        container.addView(this)
//                    }
//                }

                // 취소하기
                if (includingCancel) RequirementDrawerContainer.addCancelButton(
                    context,
                    container,
                    requirementDto
                )
            }
        }
    }
}