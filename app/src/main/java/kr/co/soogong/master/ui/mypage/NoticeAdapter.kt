package kr.co.soogong.master.ui.mypage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.ui.mypage.notice.NoticeDiffUtil
import kr.co.soogong.master.ui.mypage.notice.NoticeMyPageListViewHolder
import kr.co.soogong.master.ui.mypage.notice.NoticeMyPageListViewHolder.Companion.NoticeMypageListView
import kr.co.soogong.master.ui.mypage.notice.NoticeViewHolder
import kr.co.soogong.master.ui.mypage.notice.NoticeViewHolder.Companion.NoticeView

class NoticeAdapter(
    private val useView: Int,
    private val clickListener: (Notice) -> Unit
) : ListAdapter<Notice, RecyclerView.ViewHolder>(NoticeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (useView) {
            NoticeView -> {
                NoticeViewHolder.create(parent)
            }
            NoticeMypageListView -> {
                NoticeMyPageListViewHolder.create(parent)
            }
            else -> {
                NoticeMyPageListViewHolder.create(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)

        when (holder) {
            is NoticeViewHolder -> {
                holder.bind(data, clickListener)
            }
            is NoticeMyPageListViewHolder -> {
                holder.bind(data, clickListener)
            }
        }
    }
}