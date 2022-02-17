package kr.co.soogong.master.presentation.ui.auth.signup

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.presentation.ui.auth.signup.steps.OwnerNameFragment
import kr.co.soogong.master.presentation.ui.auth.signup.steps.PhoneNumberFragment
import kr.co.soogong.master.presentation.ui.auth.signup.steps.SignUpDoneFragment
import timber.log.Timber

class SignUpPagerAdapter(
    fragment: Fragment,
    private val totalPages: Int,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = totalPages

    override fun createFragment(position: Int): Fragment {
        Timber.tag(TAG).d("createFragment: $position")
        return when (position) {
            0 -> PhoneNumberFragment.newInstance()
            1 -> OwnerNameFragment.newInstance()
            2 -> SignUpDoneFragment.newInstance()
            else -> Fragment()
        }
    }

    companion object {
        private const val TAG = "SignUpPagerAdapter"
    }
}