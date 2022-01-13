package kr.co.soogong.master.ui.requirement.list.filter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.ui.requirement.list.TAB_TEXTS_REQUIREMENTS_BEFORE_PROGRESS
import kr.co.soogong.master.ui.requirement.list.TAB_TEXTS_REQUIREMENTS_IN_PROGRESS

class RequirementsFilterPagerAdapter(
    fragment: Fragment,
    private val type: Int,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() =
        if (type == 0) TAB_TEXTS_REQUIREMENTS_BEFORE_PROGRESS.count() else TAB_TEXTS_REQUIREMENTS_IN_PROGRESS.count()

    override fun createFragment(position: Int) = RequirementsFilterFragment.newInstance()
}