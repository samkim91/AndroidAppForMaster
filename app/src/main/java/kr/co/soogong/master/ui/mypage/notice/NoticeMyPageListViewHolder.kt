package kr.co.soogong.master.ui.mypage.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.databinding.ViewHolderNoticeMypageItemBinding

class NoticeMyPageListViewHolder(
    val binding: ViewHolderNoticeMypageItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        notice: Notice,
        clickListener: (Notice) -> Unit
    ) {
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

            title.setTextColor(color)

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