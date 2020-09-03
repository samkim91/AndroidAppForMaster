package kr.co.soogong.master.ui.requirements

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kr.co.soogong.master.ui.requirements.progress.ProgressFragment
import kr.co.soogong.master.ui.requirements.received.ReceivedFragment

class RequirementsPagerAdapter(
    fragmentManager: FragmentManager,
    private val numberOfTabs: Int
) : FragmentStatePagerAdapter(fragmentManager, numberOfTabs) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ReceivedFragment.newInstance()
            1 -> ProgressFragment.newInstance()
            else -> Fragment()
        }
    }

    override fun getCount(): Int = numberOfTabs

}