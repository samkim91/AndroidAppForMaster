package kr.co.soogong.master.ui.mypage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.ui.mypage.notice.NoticeDiffUtil
import kr.co.soogong.master.ui.mypage.notice.NoticeInMyPageViewHolder
import kr.co.soogong.master.ui.mypage.notice.NoticeInMyPageViewHolder.Companion.NoticeMypageListView
import kr.co.soogong.master.ui.mypage.notice.NoticeViewHolder
import kr.co.soogong.master.ui.mypage.notice.NoticeViewHolder.Companion.NoticeView

class NoticeAdapter(
    private val useView: Int,
    private val clickListener: (Int) -> Unit
) : ListAdapter<Notice, RecyclerView.ViewHolder>(NoticeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (useView) {
            NoticeView -> {
                NoticeViewHolder.create(parent)
            }
            NoticeMypageListView -> {
                NoticeInMyPageViewHolder.create(parent)
            }
            else -> {
                NoticeInMyPageViewHolder.create(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)

        when (holder) {
            is NoticeViewHolder -> {
                holder.bind(data, clickListener)
            }
            is NoticeInMyPageViewHolder -> {
                holder.bind(data, clickListener)
            }
        }
    }
}