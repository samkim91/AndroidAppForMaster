package kr.co.soogong.master.ui.mypage.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.databinding.ViewHolderNoticeMypageItemBinding

class NoticeMyPageListViewHolder(
    val binding: ViewHolderNoticeMypageItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        notice: Notice,
        clickListener: (Notice) -> Unit
    ) {
        binding.run {
            data = notice

            root.setOnClickListener {
                clickListener(notice)
            }
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): NoticeMyPageListViewHolder {
            val binding = ViewHolderNoticeMypageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NoticeMyPageListViewHolder(binding)
        }

        const val NoticeMypageListView = 10002
    }
}