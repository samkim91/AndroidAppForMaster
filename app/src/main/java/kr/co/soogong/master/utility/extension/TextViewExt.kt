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

//@BindingAdapter("bind:set_review_score")
//fun TextView.setReviewScore()
