package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep2Binding
import kr.co.soogong.master.databinding.FragmentSignUpStep2SubBinding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_FAILED
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGN_IN_SUCCESSFUL
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uiinterface.main.MainActivityHelper
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class Step2SubFragment : BaseFragment<FragmentSignUpStep2SubBinding>(
    R.layout.fragment_sign_up_step2_sub
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

        fun newInstance(): Step2SubFragment {
            return Step2SubFragment()
        }
    }
}