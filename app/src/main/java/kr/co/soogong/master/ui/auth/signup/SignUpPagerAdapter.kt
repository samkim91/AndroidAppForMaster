package kr.co.soogong.master.ui.auth.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.ui.auth.signup.step1.Step1Fragment
import kr.co.soogong.master.ui.auth.signup.step2.Step2Fragment
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
            1 -> Step2Fragment.newInstance()
//            2 -> Step3Fragment.newInstance()
//            3 -> Step4Fragment.newInstance()
            else -> Fragment()
        }
    }

    // Todo.. 프래그먼트간 이동하고 나서 height가 안 맞는 문제가 있다 .... 뭔가 재활용하다보니 생기는 문제 같다.


    companion object {
        private const val TAG = "SignUpPagerAdapter"
    }
}