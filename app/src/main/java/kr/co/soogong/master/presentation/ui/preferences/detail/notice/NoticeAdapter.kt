package kr.co.soogong.master.presentation.ui.preferences.detail.notice

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.domain.entity.preferences.Notice
import timber.log.Timber

class NoticeAdapter(
    private val context: Context,
    private val itemClicked: (Int) -> Unit,
) : ListAdapter<Notice, NoticeViewHolder>(NoticeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoticeViewHolder.create(parent).also {
            Timber.tag("TAG").w("onCreateViewHolder: ")
        }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) =
        holder.bind(context, currentList[position], itemClicked).also {
            Timber.tag("TAG").w("onBindViewHolder: ")
        }
}