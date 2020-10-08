package kr.co.soogong.master.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
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