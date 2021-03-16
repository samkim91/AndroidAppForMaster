package kr.co.soogong.master.ui.requirements.progress.detail.request

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentProgressRequestBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uiinterface.requirments.progress.detail.request.ProgressRequestFragmentHelper
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProgressRequestFragment : BaseFragment<FragmentProgressRequestBinding>(
    R.layout.fragment_progress_request
) {
    val keycode by lazy {
        arguments?.getString(ProgressRequestFragmentHelper.BUNDLE_KEY_RECEIVED_KEY) ?: ""
    }

    @Inject
    lateinit var factory: ProgressRequestViewModel.AssistedFactory

    val viewModel: ProgressRequestViewModel by viewModels {
        ProgressRequestViewModel.provideFactory(factory, keycode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    companion object {
        private const val TAG = "ProgressRequestFragment"

        fun newInstance(keycode: String): ProgressRequestFragment {
            val args = Bundle()
            args.putString(ProgressRequestFragmentHelper.BUNDLE_KEY_RECEIVED_KEY, keycode)

            val fragment = ProgressRequestFragment()
            fragment.arguments = args
            return fragment
        }
    }
}