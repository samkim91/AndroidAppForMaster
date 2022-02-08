package kr.co.soogong.master.presentation.ui.preferences.detail.notice

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.domain.entity.preferences.Notice

@BindingAdapter("bind:notice_list")
fun RecyclerView.setList(items: List<Notice>?) {
    (adapter as? NoticeAdapter)?.submitList(items ?: emptyList())
}