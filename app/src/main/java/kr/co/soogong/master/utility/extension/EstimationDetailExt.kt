@file:JvmName("EstimationDetailExt")

package kr.co.soogong.master.utility.extension

import android.content.Context
import android.icu.text.DecimalFormat
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.model.requirement.estimation.EstimationPriceTypeCode
import kr.co.soogong.master.ui.widget.EstimationDetail

fun addEstimationDetail(
    viewGroup: ViewGroup,
    context: Context,
    estimationDto: EstimationDto
) {
    viewGroup.removeAllViews()

    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 16.dp, 0, 0)
    }

    // 최종 시공가
    if (estimationDto.repair?.actualPrice != null) {
        val view = EstimationDetail(context)
        view.key = context.getString(R.string.repair_actual_price)
        view.value = "${DecimalFormat("#,###").format(estimationDto.repair.actualPrice)}원"
        view.bold = true

        viewGroup.addView(view, params)
    }

    // 총 견적가
    if (estimationDto.price != null) {
        val view = EstimationDetail(context)
        view.key = context.getString(R.string.estimation_total_cost)
        view.value = if (estimationDto.price != 0) {
            "${DecimalFormat("#,###").format(estimationDto.price)}원"
        } else {
            context.getString(R.string.not_estimated_text)
        }

        viewGroup.addView(view, params)
    }

    // 항목별 견적가
    if (!estimationDto.estimationPrices.isNullOrEmpty()) {
        estimationDto.estimationPrices.map {
            val view = EstimationDetail(context)

            view.key = when (it.priceTypeCode) {
                EstimationPriceTypeCode.LABOR ->
                    context.getString(R.string.estimation_labor_cost)
                EstimationPriceTypeCode.MATERIAL ->
                    context.getString(R.string.estimation_material_cost)
                EstimationPriceTypeCode.TRAVEL ->
                    context.getString(R.string.estimation_travel_cost)
                else -> ""
            }
            view.value = "${DecimalFormat("#,###").format(it.partialPrice)}원"

            viewGroup.addView(view, params)
        }
    }

    // 견적 세부내용
    if (estimationDto.description != null) {
        val view = EstimationDetail(context)
        view.key = context.getString(R.string.estimation_description_label)
        view.value = estimationDto.description

        viewGroup.addView(view, params)
    }
}