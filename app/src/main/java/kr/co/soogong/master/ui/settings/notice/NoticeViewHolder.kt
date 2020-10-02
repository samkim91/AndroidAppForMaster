package kr.co.soogong.master.ui.settings.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.databinding.ViewholderNoticeItemBinding

class NoticeViewHolder(
    val binding: ViewholderNoticeItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(notice: Notice, clickListener: (Notice) -> Unit) {
        binding.run {
            setVariable(BR.data, notice)

            root.resources.getColor(R.color.app_color, null)

            val color: Int
            if (notice.isNew) {
                noticeNewBadge.visibility = View.VISIBLE
                color = root.resources.getColor(R.color.app_color, null)
            } else {
                noticeNewBadge.visibility = View.GONE
                color = root.resources.getColor(R.color.secondary_text_color, null)
            }

            noticeTitle.setTextColor(color)
            root.setOnClickListener {
                clickListener(notice)
            }
        }
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): NoticeViewHolder {
            val binding = ViewholderNoticeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NoticeViewHolder(binding)
        }

        const val NoticeView = 10001
    }
}