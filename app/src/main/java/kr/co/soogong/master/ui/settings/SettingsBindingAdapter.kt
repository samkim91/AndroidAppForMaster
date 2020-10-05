package kr.co.soogong.master.ui.settings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.notice.Notice

@BindingAdapter("bind:notice_list")
fun RecyclerView.setList(items: List<Notice>?) {
    (adapter as? NoticeAdapter)?.submitList(items ?: emptyList())
}