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
            0 -> PhoneNumberFragment.newInstance()
            1 -> AuthFragment.newInstance()
            2 -> OwnerNameFragment.newInstance()
            3 -> MajorFragment.newInstance()
            4 -> AddressFragment.newInstance()
            5 -> ServiceAreaFragment.newInstance()
            6 -> PrivatePolicyFragment.newInstance()
            else -> Fragment()
        }
    }

    companion object {
        private const val TAG = "SignUpPagerAdapter"
    }
}