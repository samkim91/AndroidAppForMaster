package kr.co.soogong.master.ui.preferences.detail.notice

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.databinding.ViewHolderNoticeBinding

class NoticeViewHolder(
    val binding: ViewHolderNoticeBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        context: Context,
        notice: Notice,
    ) {
        binding.run {
            data = notice

            setOnItemClick {
                // 화살표 모양 정의
                ivArrow.background = if (flContentContainer.isVisible)
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_arrow_down, null)
                else
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_arrow_up, null)

                // 내용 컨테이너 보이는지 정의
                flContentContainer.isVisible = !binding.flContentContainer.isVisible
            }

            tvContent.text = Html.fromHtml(notice.content, 0)
        }
    }

    companion object {
        fun create(parent: ViewGroup) = NoticeViewHolder(
            ViewHolderNoticeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        const val NEW_NOTICE_VIEW_HOLDER = 100
    }
}