package kr.co.soogong.master.presentation.ui.requirement.list.filter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus

class RequirementsFilterPagerAdapter(
    fragment: Fragment,
    private val mainTabIndex: Int,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() =
        if (mainTabIndex == 0) TAB_TEXTS_REQUIREMENTS_BEFORE_PROGRESS.count() else TAB_TEXTS_REQUIREMENTS_IN_PROGRESS.count()

    override fun createFragment(position: Int) =
        RequirementsFilterFragment.newInstance(
            RequirementStatus.getRequirementStatusFromTabIndex(mainTabIndex, position))
}