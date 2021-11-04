package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.profile.SecretaryCodeTable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.databinding.ViewHolderRequirementItemBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.extension.formatFullDateTimeWithoutDay

// Requirement Card viewHolder 들의 부모클래스
open class RequirementCardViewHolder(
    private val binding: ViewHolderRequirementItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(
        context: Context,
        fragmentManager: FragmentManager,
        viewModel: RequirementViewModel,
        requirementCard: RequirementCard,
    ) {
        with(binding) {
            data = requirementCard

            setCardClickListener {
                context.startActivity(
                    ViewRequirementActivityHelper.getIntent(
                        context,
                        requirementCard.id
                    )
                )
            }

            // Note: additionalInfoContainer 에 있는 views 중복 방지
            if (additionalInfoContainer.isNotEmpty()) additionalInfoContainer.removeAllViews()
            measurementBadge.root.isVisible = requirementCard.typeCode == SecretaryCodeTable.code

            // 생성시간
            createdAt.text = requirementCard.createdAt.formatFullDateTimeWithoutDay()
        }
    }

    // TODO: 2021/10/13 버튼의 경우 상태에 따라 같은 버튼을 쓰는 경우가 있다. 이를 부모 뷰홀더에서 정의하고 사용하는 방법으로 리팩토링 해보자.
}

