package kr.co.soogong.master.ui.auth.password.find

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityFindPasswordBinding
import kr.co.soogong.master.ui.auth.password.find.FindPasswordViewModel.Companion.CERTIFICATION_CODE_CONFIRMED_FAILED
import kr.co.soogong.master.ui.auth.password.find.FindPasswordViewModel.Companion.CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY
import kr.co.soogong.master.ui.auth.password.find.FindPasswordViewModel.Companion.CERTIFICATION_CODE_REQUESTED_FAILED
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.auth.password.ChangePasswordActivityHelper
import kr.co.soogong.master.uiinterface.auth.password.ChangePasswordActivityHelper.FROM_SIGN_IN
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class FindPasswordActivity : BaseActivity<ActivityFindPasswordBinding>(
    R.layout.activity_find_password
) {
    private val viewModel: FindPasswordViewModel by viewModels()

    private val timer = object : CountDownTimer(180000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.timerView.text =
                "${millisUntilFinished / 1000 / 60}:${millisUntilFinished / 1000 % 60}"
        }

        override fun onFinish() {
            binding.alertExpiredCertificationTime.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
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
            lifecycleOwner = this@FindPasswordActivity

            with(actionBar) {
                title.text = getString(R.string.find_password_activity_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            phoneNumber.setButtonClickListener {
                requestCertificationCode { toast(getString(R.string.certification_code_requested)) }.let {
                    if (!binding.phoneNumber.alertVisible) viewModel.changeEnabled()
                }
            }

            requestCertificationCodeAgainText.setOnClickListener {
                requestCertificationCode { toast(getString(R.string.certification_code_requested_again)) }
            }

            defaultButton.setOnClickListener {
                viewModel.certificationCode.observe(this@FindPasswordActivity, {
                    alertInvalidCertificationCode.isVisible = it.length < 6
                })

                if (!alertWrongCertificationCode.isVisible && !alertExpiredCertificationTime.isVisible
                    && !alertInvalidCertificationCode.isVisible && !alertEmptyCertificationCode.isVisible
                ) viewModel.requestConfirmCertificationCode()
            }
        }
    }

    private fun registerEventObserve() {
        bind {
            viewModel.isEnabled.observe(this@FindPasswordActivity, { isEnabled ->
                phoneNumber.setFirstEditTextEnable = !isEnabled
                phoneNumber.buttonColor = isEnabled
                phoneNumber.buttonBackground = isEnabled
                phoneNumber.buttonText =
                    if (!isEnabled) getString(R.string.certification_text) else getString(R.string.retype_text)
                certificationCodeContainer.isVisible = isEnabled
                requestCertificationCodeAgainGroup.isVisible = isEnabled
                defaultButton.isEnabled = isEnabled
            })
        }

        viewModel.action.observe(this@FindPasswordActivity, EventObserver { event ->
            when (event) {
                CERTIFICATION_CODE_CONFIRMED_SUCCESSFULLY -> {
                    startActivity(
                        Intent(
                            ChangePasswordActivityHelper.getIntent(
                                this,
                                FROM_SIGN_IN,
                                viewModel.phoneNumber.value
                            )
                        )
                    )
                }

                // todo.. 가입되지 않은 번호에 대한 처리 필요
                CERTIFICATION_CODE_CONFIRMED_FAILED, CERTIFICATION_CODE_REQUESTED_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun requestCertificationCode(toast: () -> Unit) {
        bind {
            viewModel.phoneNumber.observe(this@FindPasswordActivity, {
                phoneNumber.alertVisible = it.length < 11
            })

            if (!phoneNumber.alertVisible) {
                startTimer()
                viewModel.requestCertificationCode()
                toast()
            }
        }
    }

    private fun startTimer() {
        // Todo.. 인증번호 요청/재요청에 대한 event callback으로 실행되도록 변경해야함.
        binding.alertExpiredCertificationTime.visibility = View.GONE
        timer.start()
    }

    private fun stopTimer() {
        timer.cancel()
    }

    companion object {
        private const val TAG = "FindPasswordActivity"
    }
}