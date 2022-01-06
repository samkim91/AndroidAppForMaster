package kr.co.soogong.master.ui.auth.signup

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.ui.SIGN_UP_STEPS
import kr.co.soogong.master.ui.auth.signup.steps.*
import timber.log.Timber

class SignUpPagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = SIGN_UP_STEPS

    override fun createFragment(position: Int): Fragment {
        Timber.tag(TAG).d("createFragment: $position")
        return when (position) {
            0 -> PhoneNumberFragment.newInstance()
            1 -> OwnerNameFragment.newInstance()
            2 -> MajorFragment.newInstance()
            3 -> AddressFragment.newInstance()
            4 -> ServiceAreaFragment.newInstance()
            5 -> RepairInPersonFragment.newInstance()
            6 -> PrivatePolicyFragment.newInstance()
            else -> Fragment()
        }
    }

    companion object {
        private const val TAG = "SignUpPagerAdapter"
    }
}