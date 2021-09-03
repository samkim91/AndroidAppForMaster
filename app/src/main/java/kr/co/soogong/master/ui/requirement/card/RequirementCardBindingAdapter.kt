package kr.co.soogong.master.ui.requirement.card

import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("setDate")
fun TextView.setCreatedAt(date: Date?) {
    // 2021.01.11 - 13:20
    date?.let {
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA)
        text = simpleDateFormat.format(it)
    }
}