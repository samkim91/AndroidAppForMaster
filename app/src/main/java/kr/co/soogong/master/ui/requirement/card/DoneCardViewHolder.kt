package kr.co.soogong.master.ui.requirement.card

import android.content.Context
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.databinding.ViewHolderRequirementCardBinding
import kr.co.soogong.master.ui.requirement.RequirementViewModel

// 완료탭의 viewHolders

// 평가완료 상태
class ClosedViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    viewModel: RequirementViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding)

// 시공취소 상태
class CanceledViewHolder(
    context: Context,
    fragmentManager: FragmentManager,
    viewModel: RequirementViewModel,
    binding: ViewHolderRequirementCardBinding,
) : RequirementCardViewHolder(context, fragmentManager, viewModel, binding)

