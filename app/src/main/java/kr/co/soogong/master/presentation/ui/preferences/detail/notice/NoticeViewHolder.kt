package kr.co.soogong.master.presentation.ui.preferences.detail.notice

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.preferences.Notice
import kr.co.soogong.master.databinding.ViewHolderNoticeBinding

class NoticeViewHolder(
    val binding: ViewHolderNoticeBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        context: Context,
        notice: Notice,
        itemClicked: (Int) -> Unit,
    ) {
        binding.run {
            data = notice

            setOnItemClick {
                spreadOrCollapseItem(context)
                itemClicked(notice.id)
            }

            tvContent.text = Html.fromHtml(notice.content, 0)
        }
    }

    fun spreadOrCollapseItem(
        context: Context,
    ) {
        binding.run {
            // 아이템 클릭 시 실행 필요한 코드 시작
            ivRedDot.isVisible = false

            // 화살표 모양 정의
            ivArrow.background = if (flContentContainer.isVisible)
                ResourcesCompat.getDrawable(context.resources, R.drawable.ic_arrow_down, null)
            else
                ResourcesCompat.getDrawable(context.resources, R.drawable.ic_arrow_up, null)

            // 내용 컨테이너 보이는지 정의
            flContentContainer.isVisible = !flContentContainer.isVisible
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