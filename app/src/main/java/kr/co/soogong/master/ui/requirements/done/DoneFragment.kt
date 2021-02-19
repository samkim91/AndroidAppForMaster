package kr.co.soogong.master.ui.requirements.done

import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsProgressBinding
import kr.co.soogong.master.ui.base.BaseFragment

class DoneFragment : BaseFragment<FragmentRequirementsProgressBinding>(
    R.layout.fragment_requirements_done
) {
    override fun initLayout() {
    }

    companion object {
        private const val TAG = "DoneFragment"

        fun newInstance(): DoneFragment {
            return DoneFragment()
        }
    }
}