package kr.co.soogong.master.ui.settings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.ui.settings.notice.Notice

@BindingAdapter("bind:settings_notice_list")
fun RecyclerView.setList(items: List<Notice>?) {
    (adapter as? SettingsNoticeAdapter)?.submitList(items ?: emptyList())
}