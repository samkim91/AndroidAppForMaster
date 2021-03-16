package kr.co.soogong.master.ui.sign.signin

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySignInBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.sign.signin.SignInViewModel.Companion.FAIL
import kr.co.soogong.master.ui.sign.signin.SignInViewModel.Companion.SUCCESS
import kr.co.soogong.master.uiinterface.main.MainActivityHelper
import kr.co.soogong.master.uiinterface.sign.find.FindInfoActivityHelper
import kr.co.soogong.master.util.EventObserver
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
            lifecycleOwner = this@SignInActivity

            with(actionBar) {
                title.text = getString(R.string.sign_in_activity_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            setSignInClick {
                val email = email.text?.toString()
                val password = password.text?.toString()
                if (email.isNullOrEmpty()) {
                    signInAlert.visibility = View.VISIBLE
                } else if (password.isNullOrEmpty()) {
                    signInAlert.visibility = View.VISIBLE
                } else if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    viewModel.getUserInfo(email, password)
                }
            }

            setFindInfoClick {
                startActivity(FindInfoActivityHelper.getIntent(this@SignInActivity))
            }

            email.addTextChangedListener(
                afterTextChanged = {
                    if (signInAlert.visibility == View.VISIBLE && !email.text.isNullOrEmpty()) {
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
        viewModel.event.observe(this, EventObserver { event ->
            when (event) {
                SUCCESS -> {
                    startActivity(MainActivityHelper.getIntent(this))
                    finish()
                }
                FAIL -> {
                    binding.signInAlert.visibility = View.VISIBLE
                }
            }
        })
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}