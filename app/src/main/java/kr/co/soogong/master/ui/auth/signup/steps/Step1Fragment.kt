package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep1Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class Step1Fragment : BaseFragment<FragmentSignUpStep1Binding>(
    R.layout.fragment_sign_up_step1
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

            defaultButton.setOnClickListener {
                viewModel.phoneNumber.observe(viewLifecycleOwner, {
                    phoneNumber.alertVisible = it.isNullOrEmpty()
                })

                if (!phoneNumber.alertVisible) {
                    if (!BuildConfig.DEBUG) {
                        // Todo.. SMS 인증코드 전송 개발 필요.. AWS Cognito 이용
                    }


                    requireContext().toast(getString(R.string.certification_code_requested))
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")


    }

    companion object {
        private const val TAG = "Step1Fragment"

        fun newInstance(): Step1Fragment {
            return Step1Fragment()
        }
    }
}