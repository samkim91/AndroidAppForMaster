package kr.co.soogong.master.presentation.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpDoneBinding
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.MOVE_TO_MAIN_ACTIVITY
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.uihelper.main.MainActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class SignUpDoneFragment : BaseFragment<FragmentSignUpDoneBinding>(
    R.layout.fragment_sign_up_done
) {
    private val viewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserver()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")
        viewModel.message.observe(viewLifecycleOwner) { (key, _) ->
            when (key) {
                MOVE_TO_MAIN_ACTIVITY -> startActivity(MainActivityHelper.getIntent(requireContext()))
            }
        }
    }


    companion object {
        private const val TAG = "SignUpDoneFragment"

        fun newInstance() = SignUpDoneFragment()
    }
}