package kr.co.soogong.master.ui.preferences.detail.notice

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.model.mypage.Notice

class NoticeAdapter(
    private val context: Context,
) : ListAdapter<Notice, NoticeViewHolder>(NoticeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoticeViewHolder.create(parent)

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) =
        holder.bind(context, currentList[position])
}