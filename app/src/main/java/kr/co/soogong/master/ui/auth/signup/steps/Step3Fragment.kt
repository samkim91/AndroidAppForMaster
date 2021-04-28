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
import kr.co.soogong.master.databinding.FragmentSignUpStep3Binding
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
class Step3Fragment : BaseFragment<FragmentSignUpStep3Binding>(
    R.layout.fragment_sign_up_step3
) {
    private val viewModel: SignUpViewModel by activityViewModels()

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

            defaultButton.setOnClickListener {
                viewModel.signInPassword.observe(viewLifecycleOwner, {
                    signUpPassword.alertVisible = it.length < 6
                })

                viewModel.signUpConfirmPassword.observe(viewLifecycleOwner, {
                    signUpConfirmPassword.alertVisible = viewModel.signInPassword.value != it
                })

//                if (!signUpPassword.alertVisible && !signUpConfirmPassword.alertVisible){
                (activity as? SignUpActivity)?.moveToNext()
//                }
            }
        }
    }

    companion object {
        private const val TAG = "Step3Fragment"

        fun newInstance(): Step3Fragment {
            return Step3Fragment()
        }
    }
}