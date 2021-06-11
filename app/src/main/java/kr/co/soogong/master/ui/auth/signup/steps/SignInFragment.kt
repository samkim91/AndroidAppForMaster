package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpSignInBinding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_FAILED
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_SUCCESSFUL
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignUpSignInBinding>(
    R.layout.fragment_sign_up_sign_in
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            findingPasswordTextUnderline.setOnClickListener {
                //Todo.. finding password로 연결

            }

            defaultButton.setOnClickListener {
                viewModel.signInPassword.observe(viewLifecycleOwner, {
                    password.alertVisible = it.isNullOrEmpty()
                })

                if (!password.alertVisible){
                    viewModel.requestLogin()
                }
            }
        }
    }


    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when(event) {
                SIGN_IN_SUCCESSFUL -> {
                    startActivity(MainActivityHelper.getIntent(requireContext()))
                }
                SIGN_IN_FAILED -> {
                    binding.alertInvalidIdOrPassword.visibility = View.VISIBLE
                }
            }
        })
    }

    companion object {
        private const val TAG = "Step2SubFragment"

        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }
}