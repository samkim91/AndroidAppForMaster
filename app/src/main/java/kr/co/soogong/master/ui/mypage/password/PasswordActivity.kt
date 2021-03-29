package kr.co.soogong.master.ui.mypage.password

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityPasswordBinding
import kr.co.soogong.master.util.extension.toast
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class PasswordActivity : BaseActivity<ActivityPasswordBinding>(
    R.layout.activity_password
) {
    private val viewModel: PasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@PasswordActivity

            with(actionBar) {
                title.text = getString(R.string.password_page_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            password.addTextChangedListener(afterTextChanged = {
                if (!password.text.isNullOrEmpty()) {
                    hidePasswordAlert()
                } else {
                    showPasswordAlert(getString(R.string.password_empty_alert_text))
                }
            })

            passwordChange.addTextChangedListener(afterTextChanged = {
                if (!passwordChange.text.isNullOrEmpty()) {
                    hidePasswordAlert()
                } else {
                    showPasswordAlert(getString(R.string.password_empty_alert_text))
                }
            })
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                PasswordViewModel.SUCCESS_PASSWORD -> {
                    toast("비밀번호 변경 성공")
                }
                PasswordViewModel.FAIL_PASSWORD -> {
                    showPasswordAlert(getString(R.string.password_empty_alert_text))
                }
                PasswordViewModel.FAIL_PASSWORD_NOT_MATCH -> {
                    showPasswordAlert(getString(R.string.password_compare_alert_text))
                }
            }

        })
    }

    private fun showPasswordAlert(message: String) {
        binding.passwordAlert.text = message
        binding.passwordAlert.visibility = View.VISIBLE
    }

    private fun hidePasswordAlert() {
        binding.passwordAlert.visibility = View.GONE
    }

    companion object {
        private const val TAG = "PasswordActivity"
    }
}