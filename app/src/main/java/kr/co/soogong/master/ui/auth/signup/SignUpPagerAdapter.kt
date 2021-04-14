package kr.co.soogong.master.ui.auth.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.ui.auth.signup.step1.Step1Fragment
import kr.co.soogong.master.ui.mypage.MyPageFragment
import kr.co.soogong.master.ui.profile.ProfileFragment
import kr.co.soogong.master.ui.requirements.RequirementsFragment
import timber.log.Timber

class SignUpPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = TabCount

    override fun createFragment(position: Int): Fragment {
        Timber.tag(TAG).d("createFragment: $position")
        return when (position) {
            0 -> Step1Fragment.newInstance()
//            1 -> ProfileFragment.newInstance()
//            2 -> MyPageFragment.newInstance()
            else -> Fragment()
        }
    }

    companion object {
        private const val TAG = "SignUpPagerAdapter"
    }
}