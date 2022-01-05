package kr.co.soogong.master.ui.auth.signmain

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignMainBinding
import kr.co.soogong.master.ui.auth.AuthContainerViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class SignMainFragment : BaseFragment<FragmentSignMainBinding>(
    R.layout.fragment_sign_main
) {
    private val viewModel: AuthContainerViewModel by activityViewModels()

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
        private const val TAG = "SignMainFragment"

        fun newInstance() = SignMainFragment()
    }
}