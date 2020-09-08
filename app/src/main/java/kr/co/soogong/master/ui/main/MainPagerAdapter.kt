package kr.co.soogong.master.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kr.co.soogong.master.ui.profile.ProfileFragment
import kr.co.soogong.master.ui.requirements.RequirementsFragment
import kr.co.soogong.master.ui.settings.SettingsFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val numberOfTabs: Int
) : FragmentStatePagerAdapter(fragmentManager, numberOfTabs) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RequirementsFragment.newInstance()
//            1 -> MaterialFragment.newInstance()
            1 -> ProfileFragment.newInstance()
            2 -> SettingsFragment.newInstance()
            else -> Fragment()
        }
    }

    override fun getCount(): Int = numberOfTabs
}