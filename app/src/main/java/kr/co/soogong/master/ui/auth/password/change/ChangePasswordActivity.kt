package kr.co.soogong.master.ui.auth.password.change

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityChangePasswordBinding
import kr.co.soogong.master.ui.auth.password.change.ChangePasswordViewModel.Companion.PASSWORD_CHANGED_FAILED
import kr.co.soogong.master.ui.auth.password.change.ChangePasswordViewModel.Companion.PASSWORD_CHANGED_SUCCESSFULLY
import kr.co.soogong.master.ui.auth.password.change.ChangePasswordViewModel.Companion.SIGN_IN_FAILED
import kr.co.soogong.master.ui.auth.password.change.ChangePasswordViewModel.Companion.SIGN_IN_SUCCESSFULLY
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.auth.password.ChangePasswordActivityHelper
import kr.co.soogong.master.uihelper.auth.password.ChangePasswordActivityHelper.FROM_MY_PAGE
import kr.co.soogong.master.uihelper.auth.password.ChangePasswordActivityHelper.FROM_SIGN_IN
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>(
    R.layout.activity_change_password
) {
    private val activityFrom: String by lazy {
        ChangePasswordActivityHelper.getActivityFrom(intent)
    }

    private val id: String by lazy {
        ChangePasswordActivityHelper.getId(intent)
    }

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@ChangePasswordActivity

            with(actionBar) {
                title.text = getString(R.string.change_password_activity_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            defaultButton.setOnClickListener {
                viewModel.password.observe(this@ChangePasswordActivity, {
                    passwordInput.alertVisible = it.length < 6
                })

                viewModel.confirmPassword.observe(this@ChangePasswordActivity, {
                    confirmPasswordInput.alertVisible = viewModel.password.value != it
                })

                if(!passwordInput.alertVisible && !confirmPasswordInput.alertVisible && defaultButton.isEnabled) viewModel.requestChangePassword()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.password.observe(this@ChangePasswordActivity, {
            binding.defaultButton.isEnabled = it.isNotEmpty()
        })

        viewModel.action.observe(this@ChangePasswordActivity, EventObserver { event ->
            when(event) {
                PASSWORD_CHANGED_SUCCESSFULLY -> {
                    toast(getString(R.string.password_changed_successfully))
                    when(activityFrom) {
                        FROM_SIGN_IN -> {
                            viewModel.signIn(id = id)
                        }
                        FROM_MY_PAGE -> {
                            super.onBackPressed()
                        }
                    }
                }
                PASSWORD_CHANGED_FAILED, SIGN_IN_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
                SIGN_IN_SUCCESSFULLY -> {
                    startActivity(MainActivityHelper.getIntent(this))
                }
            }
        })
    }

    companion object {
        private const val TAG = "ChangePasswordActivity"
    }
}