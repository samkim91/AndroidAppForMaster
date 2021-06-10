package kr.co.soogong.master.ui.mypage.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.databinding.ViewHolderNoticeItemBinding

class NoticeViewHolder(
    val binding: ViewHolderNoticeItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        notice: Notice,
        clickListener: (Notice) -> Unit
    ) {
        binding.run {
            data = notice

            if (notice.isNew) {
                noticeNewBadge.visibility = View.VISIBLE
            } else {
                noticeNewBadge.visibility = View.GONE
            }

            root.setOnClickListener {
                clickListener(notice)
            }
        }
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): NoticeViewHolder {
            val binding = ViewHolderNoticeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NoticeViewHolder(binding)
        }

        const val NoticeView = 10001
    }
}