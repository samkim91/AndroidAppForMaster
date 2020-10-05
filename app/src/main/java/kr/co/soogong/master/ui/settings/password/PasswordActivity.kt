package kr.co.soogong.master.ui.settings.password

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityPasswordBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class PasswordActivity : BaseActivity<ActivityPasswordBinding>(
    R.layout.activity_password
) {
    private val viewModel: PasswordViewModel by lazy {
        ViewModelProvider(this).get(PasswordViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@PasswordActivity

            with(actionBar) {
                title.text = "비밀번호 변경"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            inputPassword.addTextChangedListener(afterTextChanged = {
                inputPassword.hintVisible = inputPassword.text.isNullOrEmpty()
            })

            inputPasswordConfirm.addTextChangedListener(afterTextChanged = {
                if (inputPassword.text.isNullOrEmpty()) {
                    return@addTextChangedListener
                }
                if (inputPasswordConfirm.text.isNullOrEmpty()) {
                    return@addTextChangedListener
                }
                inputPasswordConfirm.hintVisible = inputPassword.text != inputPasswordConfirm.text
            })

            setChangeClick {
                val password = inputPassword.text
                val confirmPassword = inputPasswordConfirm.text

                if (password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
                    return@setChangeClick
                }

                if (password != confirmPassword) {
                    inputPasswordConfirm.hintVisible = true
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