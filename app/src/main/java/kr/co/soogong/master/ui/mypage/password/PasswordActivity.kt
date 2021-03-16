package kr.co.soogong.master.ui.mypage.password

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityPasswordBinding
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
                    passwordAlert.visibility = View.GONE
                } else {
                    passwordAlert.text = getString(R.string.password_empty_alert_text)
                    passwordAlert.visibility = View.VISIBLE
                }
            })

            passwordChange.addTextChangedListener(afterTextChanged = {
                if (!passwordChange.text.isNullOrEmpty()) {
                    passwordAlert.visibility = View.GONE
                } else {
                    passwordAlert.text = getString(R.string.password_empty_alert_text)
                    passwordAlert.visibility = View.VISIBLE
                }
            })

            setChangeClick {
                val password = password.text?.toString()
                val confirmPassword = passwordCheck.text?.toString()

                if (password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
                    passwordAlert.text = getString(R.string.password_empty_alert_text)
                    passwordAlert.visibility = View.VISIBLE
                    return@setChangeClick
                }

                if (password != confirmPassword) {
                    passwordAlert.text = getString(R.string.password_compare_alert_text)
                    passwordAlert.visibility = View.VISIBLE
                    return@setChangeClick
                }

                viewModel.resetPassword(password, confirmPassword)
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.completeEvent.observe(this, EventObserver {
            Toast.makeText(this@PasswordActivity, "비밀번호 변경 성공", Toast.LENGTH_LONG).show()
        })
    }

    companion object {
        private const val TAG = "PasswordActivity"
    }
}