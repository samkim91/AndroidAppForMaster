package kr.co.soogong.master.presentation.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.presentation.ui.home.HomeFragment
import kr.co.soogong.master.presentation.ui.preferences.PreferencesFragment
import kr.co.soogong.master.presentation.ui.profile.ProfileFragment
import kr.co.soogong.master.presentation.ui.requirement.RequirementFragment
import timber.log.Timber

class MainPagerAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = TabCount

    override fun createFragment(position: Int): Fragment {
        Timber.tag(TAG).d("createFragment: $position")
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> RequirementFragment.newInstance()
            2 -> ProfileFragment.newInstance()
            3 -> PreferencesFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }

    companion object {
        private const val TAG = "MainPagerAdapter"
    }
}