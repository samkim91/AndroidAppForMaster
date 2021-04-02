@file:JvmName("addTransmissionMessageExt")

package kr.co.soogong.master.util.extension

import android.content.Context
import android.icu.text.DecimalFormat
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.data.estimation.Message
import kr.co.soogong.master.ui.widget.TransmissionMessage

fun addTransmissionMessage(
        viewGroup: ViewGroup,
        context: Context,
        message: Message
) {
    val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    )

    params.setMargins(0, 12.dp, 0, 12.dp)

    // todo.. 최종 시공가격도 포함되도록 서버 return 수정 필요

    if (message.priceInNumber != null) {
        val view = TransmissionMessage(context)
        view.key = context.getString(R.string.view_estimate_transmission_detail_total_price)
        view.value = message.priceInNumber.toString()

        viewGroup.addView(view, params)
    }

    if (!message.priceDetail.isNullOrEmpty()) {
        message.priceDetail.forEach { item ->
            val view = TransmissionMessage(context)

            when (item.priceType) {
                "personnel" ->
                    view.key = context.getString(R.string.view_estimate_transmission_detail_personnel_price)
                "material" ->
                    view.key = context.getString(R.string.view_estimate_transmission_detail_material_price)
                "trip" ->
                    view.key = context.getString(R.string.view_estimate_transmission_detail_trip_price)
            }
            view.value = "${DecimalFormat("#,###").format(item.priceInNumber ?: 0)}원"

            viewGroup.addView(view, params)
        }
    }

    if (message.contents != null) {
        val view = TransmissionMessage(context)
        view.key = context.getString(R.string.view_estimate_transmission_detail_contents)
        view.value = message.contents

        viewGroup.addView(view, params)
    }

    //TODO.. 시공시기도 포함되도록 서버 return 수정 필요

}