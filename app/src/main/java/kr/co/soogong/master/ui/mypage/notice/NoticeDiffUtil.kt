package kr.co.soogong.master.ui.mypage.notice

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.notice.Notice

class NoticeDiffUtil : DiffUtil.ItemCallback<Notice>() {
    override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
        return oldItem == newItem
    }
}