package kr.co.soogong.master.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentMypageBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.requirements.RequirementsFragment
import timber.log.Timber

class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    R.layout.fragment_mypage
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
    }

    companion object {
        private const val TAG = "MyPageFragment"

        fun newInstance(): MyPageFragment {
            val args = Bundle()

            val fragment = MyPageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}