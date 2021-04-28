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
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class Step2Fragment : BaseFragment<FragmentSignUpStep2Binding>(
    R.layout.fragment_sign_up_step2
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private val timer = object: CountDownTimer(180000,1000){
        override fun onTick(millisUntilFinished: Long) {
            binding.timerView.text = "${millisUntilFinished/1000/60}:${millisUntilFinished/1000%60}"
        }

        override fun onFinish() {
            binding.alertExpiredCertificationTime.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        startTimer()
        registerEventObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            requestCertificationCodeAgainText.setOnClickListener {
                timer.start()
                binding.alertExpiredCertificationTime.visibility = View.GONE

                if (!BuildConfig.DEBUG) {
                    // Todo.. SMS 인증코드 전송 개발 필요.. AWS Cognito 이용

                }

                requireContext().toast(getString(R.string.certification_code_requested_again))
            }

            leftButton.setOnClickListener {
                (activity as? SignUpActivity)?.moveToPrevious()
            }

            rightButton.setOnClickListener {
                viewModel.certificationCode.observe(viewLifecycleOwner, {
                    alertInvalidCertificationCode.visibility = if (it < 1000000) View.VISIBLE else View.GONE
                })

                if(!alertWrongCertificationCode.isVisible && !alertExpiredCertificationTime.isVisible && !alertInvalidCertificationCode.isVisible){
                    if (!BuildConfig.DEBUG) {
                        // Todo.. 인증번호 확인하는 부분 작성 필요

                    }
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    private fun startTimer() {
        // Todo.. 인증번호 요청/재요청에 대한 event callback으로 실행되도록 변경해야함.
        timer.start()
    }

    private fun stopTimer() {
        timer.cancel()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    companion object {
        private const val TAG = "Step2Fragment"

        fun newInstance(): Step2Fragment {
            return Step2Fragment()
        }
    }
}