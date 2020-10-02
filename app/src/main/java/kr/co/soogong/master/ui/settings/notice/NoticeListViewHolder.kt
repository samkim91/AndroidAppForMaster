package kr.co.soogong.master.ui.settings.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.databinding.ViewholderNoticeListItemBinding

class NoticeListViewHolder(
    val binding: ViewholderNoticeListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(notice: Notice, clickListener: (Notice) -> Unit) {
        binding.run {
            setVariable(BR.data, notice)

            root.resources.getColor(R.color.app_color, null)

            val color = root.resources.getColor(
                if (notice.isNew) {
                    R.color.app_color
                } else {
                    R.color.secondary_text_color
                },
                null
            )

            noticeTitle.setTextColor(color)

            root.setOnClickListener {
                clickListener(notice)
            }
        }
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): NoticeListViewHolder {
            val binding = ViewholderNoticeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NoticeListViewHolder(binding)
        }

        const val NoticeListView = 10002
    }
}