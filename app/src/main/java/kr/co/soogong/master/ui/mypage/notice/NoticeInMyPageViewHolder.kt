package kr.co.soogong.master.ui.mypage.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.databinding.ViewHolderNoticeInMypageItemBinding

class NoticeInMyPageViewHolder(
    val binding: ViewHolderNoticeInMypageItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        notice: Notice,
        clickListener: (Int) -> Unit
    ) {
        binding.run {
            data = notice

            root.setOnClickListener {
                clickListener(notice.id)
            }
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): NoticeInMyPageViewHolder {
            val binding = ViewHolderNoticeInMypageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NoticeInMyPageViewHolder(binding)
        }

        const val NoticeMypageListView = 10002
    }
}