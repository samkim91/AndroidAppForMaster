package kr.co.soogong.master.ui.requirements

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.ui.requirements.done.DoneFragment
import kr.co.soogong.master.ui.requirements.progress.ProgressFragment
import kr.co.soogong.master.ui.requirements.received.ReceivedFragment

class RequirementsPagerAdapter(
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