package kr.co.soogong.master.ui.mypage

import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.mypage.Notice

@BindingAdapter("bind:notice_list")
fun RecyclerView.setList(items: List<Notice>?) {
    (adapter as? NoticeAdapter)?.submitList(items ?: emptyList())
}

@BindingAdapter("bind:notice_list_new_visible")
fun FrameLayout.setVisible(items: List<Notice>?) {
    visibility = if (items?.find { notice -> notice.isNew } != null) {
        View.VISIBLE
    } else {
        View.GONE
    }
}