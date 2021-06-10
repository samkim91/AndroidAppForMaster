package kr.co.soogong.master.ui.requirement

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.ui.requirement.done.DoneFragment
import kr.co.soogong.master.ui.requirement.progress.ProgressFragment
import kr.co.soogong.master.ui.requirement.received.ReceivedFragment

class RequirementPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = TabCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReceivedFragment.newInstance()
            1 -> ProgressFragment.newInstance()
            2 -> DoneFragment.newInstance()
            else -> Fragment()
        }
    }
}