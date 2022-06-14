package kr.co.soogong.master.presentation.ui.requirement.list.filter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RequirementsFilterPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = FILTER_TEXTS_IN_REQUIREMENT_FRAGMENT.count()

    override fun createFragment(position: Int) =
        RequirementsFilterFragment.newInstance(FILTER_TEXTS_IN_REQUIREMENT_FRAGMENT[position].second)
}