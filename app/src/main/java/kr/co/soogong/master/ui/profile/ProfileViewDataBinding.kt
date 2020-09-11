package kr.co.soogong.master.ui.profile

import androidx.databinding.BindingAdapter
import kr.co.soogong.master.ui.widget.TitleTextView

@BindingAdapter("bind:profile_detail_text")
fun TitleTextView.setProfileDetailText(text: String?) {
    setText(text ?: "")
}
