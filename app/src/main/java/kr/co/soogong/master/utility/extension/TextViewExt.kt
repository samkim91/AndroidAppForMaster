@file:JvmName("TextViewExt")

package kr.co.soogong.master.utility.extension

import android.content.Context
import android.icu.text.DecimalFormat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bind:to_datetime_string")
fun TextView.setDateTime(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd hh:mm")
    text = simpleDateFormat.format(date ?: System.currentTimeMillis())
}

@BindingAdapter("bind:to_date_string")
fun TextView.setDate(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd")
    text = simpleDateFormat.format(date ?: System.currentTimeMillis())
}

@BindingAdapter("bind:set_price")
fun TextView.setPrice(price: Int?) {
    price?.let {
        text = "${DecimalFormat("#,###").format(it)}원"
    }
}


fun addTextView3(
    viewGroup: ViewGroup,
    context: Context,
    message: String?
) {
    val contextWrapper =
        ContextThemeWrapper(context, R.style.medium_text_style_regular)

    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        40.dp
    )
    params.setMargins(0, 8.dp, 0, 0)

    val view = AppCompatTextView(contextWrapper).apply {
        setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text_color, null))
        background =
            ResourcesCompat.getDrawable(resources, R.drawable.shape_fill_white_background, null)
        gravity = Gravity.CENTER
        text = message
    }

    viewGroup.addView(view, params)
}