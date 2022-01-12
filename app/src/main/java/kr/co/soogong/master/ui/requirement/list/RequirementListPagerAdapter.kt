package kr.co.soogong.master.ui.requirement.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RequirementListPagerAdapter(
    fragment: Fragment,
    private val type: Int,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() =
        if (type == 0) TAB_TEXTS_REQUIREMENTS_BEFORE_PROGRESS.count() else TAB_TEXTS_REQUIREMENTS_IN_PROGRESS.count()

    override fun createFragment(position: Int) = RequirementListFragment.newInstance()
}