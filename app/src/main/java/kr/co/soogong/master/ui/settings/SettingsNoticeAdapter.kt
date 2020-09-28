package kr.co.soogong.master.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewholderNoticeListItemBinding
import kr.co.soogong.master.ui.settings.notice.Notice
import kr.co.soogong.master.ui.settings.notice.NoticeDiffUtil

class SettingsNoticeAdapter : ListAdapter<Notice, NoticeListViewHolder>(NoticeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
        val binding = ViewholderNoticeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoticeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
        holder.binding.run {
            val item = currentList[position]
            vm = item

            root.resources.getColor(R.color.app_color, null)

            val color = root.resources.getColor(
                if (item.isNew) {
                    R.color.app_color
                } else {
                    R.color.secondary_text_color
                },
                null
            )

            noticeTitle.setTextColor(color)
        }
    }
}