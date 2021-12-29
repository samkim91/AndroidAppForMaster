package kr.co.soogong.master.ui.preferences.detail.notice

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.ui.preferences.detail.notice.NoticeAdapter

@BindingAdapter("bind:notice_list")
fun RecyclerView.setList(items: List<Notice>?) {
    (adapter as? NoticeAdapter)?.submitList(items ?: emptyList())
}