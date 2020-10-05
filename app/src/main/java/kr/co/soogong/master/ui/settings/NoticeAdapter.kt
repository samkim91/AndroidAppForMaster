package kr.co.soogong.master.ui.settings

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.ui.settings.notice.NoticeDiffUtil
import kr.co.soogong.master.ui.settings.notice.NoticeListViewHolder
import kr.co.soogong.master.ui.settings.notice.NoticeListViewHolder.Companion.NoticeListView
import kr.co.soogong.master.ui.settings.notice.NoticeViewHolder
import kr.co.soogong.master.ui.settings.notice.NoticeViewHolder.Companion.NoticeView

class NoticeAdapter(
    private val useView: Int = NoticeListView,
    private val clickListener: (Notice) -> Unit
) : ListAdapter<Notice, RecyclerView.ViewHolder>(NoticeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (useView) {
            NoticeView -> {
                NoticeViewHolder.create(parent)
            }
            NoticeListView -> {
                NoticeListViewHolder.create(parent)
            }
            else -> {
                NoticeListViewHolder.create(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)

        when (holder) {
            is NoticeViewHolder -> {
                holder.bind(data, clickListener)
            }
            is NoticeListViewHolder -> {
                holder.bind(data, clickListener)
            }
        }
    }
}