package kr.co.soogong.master.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySignMainBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.auth.signin.SignInActivityHelper
import kr.co.soogong.master.uihelper.auth.signup.SignUpActivityHelper
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class SignMainActivity : BaseActivity<ActivitySignMainBinding>(
    R.layout.activity_sign_main
) {

    private val viewModel: SignMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@SignMainActivity
            vm = viewModel
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                SignMainViewModel.SIGNUP -> {
                    startActivity(SignUpActivityHelper.getIntent(this@SignMainActivity))
                }
                SignMainViewModel.SIGNIN -> {
                    startActivity(SignInActivityHelper.getIntent(this@SignMainActivity))
                }
            }
        })
    }

    companion object {
        private const val TAG = "SignMainActivity"
    }
}