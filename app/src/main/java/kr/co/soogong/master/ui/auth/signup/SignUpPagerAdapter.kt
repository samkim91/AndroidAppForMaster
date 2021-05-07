package kr.co.soogong.master.ui.auth.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.ui.auth.signup.steps.*
import timber.log.Timber

class SignUpPagerAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = TabCount

    override fun createFragment(position: Int): Fragment {
        Timber.tag(TAG).d("createFragment: $position")
        return when (position) {
            0 -> Step1Fragment.newInstance()
            1 -> Step2Fragment.newInstance()
            2 -> Step3Fragment.newInstance()
            3 -> Step4Fragment.newInstance()
            4 -> Step5Fragment.newInstance()
            5 -> Step6Fragment.newInstance()
//            6 -> Step7Fragment.newInstance()
            6 -> Step8Fragment.newInstance()
            else -> Fragment()
        }
    }

    companion object {
        private const val TAG = "SignUpPagerAdapter"
    }
}