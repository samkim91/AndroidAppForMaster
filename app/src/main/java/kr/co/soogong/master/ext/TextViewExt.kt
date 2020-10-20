package kr.co.soogong.master.ext

import android.content.Context
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

@BindingAdapter("bind:date_text")
fun TextView.setDateTime(date: Date) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd hh:mm")
    text = simpleDateFormat.format(date)
}

@BindingAdapter("bind:date_text2")
fun TextView.setDateTime2(date: Date) {
    val simpleDateFormat = SimpleDateFormat("yyyy. MM. dd")
    text = simpleDateFormat.format(date)
}

fun addTextView(
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
            ResourcesCompat.getDrawable(resources, R.drawable.title_textview_background, null)
        gravity = Gravity.CENTER
        text = message
    }

    viewGroup.addView(view, params)
}