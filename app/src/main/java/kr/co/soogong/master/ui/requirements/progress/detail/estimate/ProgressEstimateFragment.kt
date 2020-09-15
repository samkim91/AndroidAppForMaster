package kr.co.soogong.master.ui.requirements.progress.detail.estimate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentProgressEstimateBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.uiinterface.requirments.progress.detail.estimate.ProgressEstimateFragmentHelper
import timber.log.Timber

class ProgressEstimateFragment : BaseFragment<FragmentProgressEstimateBinding>(
    R.layout.fragment_progress_estimate
) {
    val keycode by lazy {
        arguments?.getString(ProgressEstimateFragmentHelper.BUNDLE_KEY_RECEIVED_KEY) ?: ""
    }

    val viewModel: ProgressEstimateViewModel by viewModels {
        ProgressEstimateViewModelFactory(getRepository(requireContext()), keycode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
    }

    companion object {
        private const val TAG = "ProgressEstimateFragmen"

        fun newInstance(keycode: String): ProgressEstimateFragment {
            val args = Bundle()
            args.putString(ProgressEstimateFragmentHelper.BUNDLE_KEY_RECEIVED_KEY, keycode)
            val fragment = ProgressEstimateFragment()
            fragment.arguments = args
            return fragment
        }
    }
}