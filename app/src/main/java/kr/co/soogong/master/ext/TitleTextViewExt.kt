package kr.co.soogong.master.ext

import androidx.databinding.BindingAdapter
import kr.co.soogong.master.ui.widget.TitleTextView

@BindingAdapter("bind:detail_text")
fun TitleTextView.setDetailText(text: String?) {
    setText(text ?: "")
}