package kr.co.soogong.master.ui.requirements.progress.detail.request

import androidx.databinding.BindingAdapter
import kr.co.soogong.master.ui.widget.TitleTextView2
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bind:progress_request_detail_text")
fun TitleTextView2.setProgressRequestDetailText(text: String?) {
    setText(text ?: "")
}

@BindingAdapter("bind:progress_request_detail_date")
fun TitleTextView2.setProgressRequestDetailDate(date: Date?) {
    val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
    setText(simpleDateFormat.format(date ?: System.currentTimeMillis()))
}
