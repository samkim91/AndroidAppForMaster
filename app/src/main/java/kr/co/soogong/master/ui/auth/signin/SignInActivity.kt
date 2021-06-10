package kr.co.soogong.master.ui.auth.signin

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySignInBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.auth.password.FindPasswordActivityHelper
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(
    R.layout.activity_sign_in
) {
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@SignInActivity

            with(actionBar) {
                title.text = getString(R.string.sign_in_activity_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            id.addTextChangedListener(
                afterTextChanged = {
                    if (signInAlert.visibility == View.VISIBLE && !id.text.isNullOrEmpty()) {
                        signInAlert.visibility = View.GONE
                    }
                }
            )

            password.addTextChangedListener(
                afterTextChanged = {
                    if (signInAlert.visibility == View.VISIBLE && !password.text.isNullOrEmpty()) {
                        signInAlert.visibility = View.GONE
                    }
                }
            )
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                SignInViewModel.FIND -> {
                    startActivity(FindPasswordActivityHelper.getIntent(this@SignInActivity))
                }
                SignInViewModel.SUCCESS -> {
                    startActivity(MainActivityHelper.getIntent(this))
                }
                SignInViewModel.FAIL -> {
                    binding.signInAlert.visibility = View.VISIBLE
                }
                SignInViewModel.INVALID_INPUT -> {
                    binding.signInAlert.visibility = View.VISIBLE
                }
            }
        })
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}