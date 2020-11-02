package kr.co.soogong.master.ui.all

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentAllBinding
import kr.co.soogong.master.ui.base.BaseFragment

class AllFragment : BaseFragment<FragmentAllBinding>(
    R.layout.fragment_all
) {
    override fun initLayout() {

    }

    companion object {
        fun newInstance(): AllFragment {
            val args = Bundle()

            val fragment = AllFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

