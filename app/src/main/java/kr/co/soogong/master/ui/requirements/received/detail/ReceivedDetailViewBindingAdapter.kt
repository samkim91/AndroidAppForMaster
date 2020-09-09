package kr.co.soogong.master.ui.requirements.received.detail

import androidx.databinding.BindingAdapter
import kr.co.soogong.master.ui.widget.TitleTextView2
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bind:detail_text")
fun TitleTextView2.setDetailText(text: String?) {
    setText(text ?: "")
}

@BindingAdapter("bind:detail_date")
fun TitleTextView2.setDetailDate(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
    setText(simpleDateFormat.format(date ?: System.currentTimeMillis()))
}
