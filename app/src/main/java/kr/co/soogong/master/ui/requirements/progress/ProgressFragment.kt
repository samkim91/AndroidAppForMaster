package kr.co.soogong.master.ui.requirements.progress

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsProgressBinding
import kr.co.soogong.master.ui.base.BaseFragment

class ProgressFragment : BaseFragment<FragmentRequirementsProgressBinding>(
    R.layout.fragment_requirements_progress
) {
    companion object {
        fun newInstance(): ProgressFragment {
            val args = Bundle()

            val fragment = ProgressFragment()
            fragment.arguments = args
            return fragment
        }
    }
}