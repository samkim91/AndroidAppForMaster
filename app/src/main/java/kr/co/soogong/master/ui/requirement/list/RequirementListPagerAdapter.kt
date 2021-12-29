package kr.co.soogong.master.ui.requirement.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RequirementListPagerAdapter(
    fragment: Fragment,
    private val type: Int,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() =
        if (type == 0) TAB_COUNT_REQUIREMENTS_BEFORE_PROGRESS else TAB_COUNT_REQUIREMENTS_IN_PROGRESS

    override fun createFragment(position: Int) = RequirementListFragment.newInstance()
}