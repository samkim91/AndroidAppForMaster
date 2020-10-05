package kr.co.soogong.master.ui.sign

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySignMainBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.sign.signin.SignInActivityHelper
import kr.co.soogong.master.uiinterface.sign.signup.SignUpActivityHelper
import timber.log.Timber

class SignMainActivity : BaseActivity<ActivitySignMainBinding>(
    R.layout.activity_sign_main
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@SignMainActivity

            setSignInClick {
                Timber.tag(TAG).d("initLayout: SignInClick")
                startActivity(SignInActivityHelper.getIntent(this@SignMainActivity))
            }

            setSignUpClick {
                Timber.tag(TAG).d("initLayout: SignUpClick")
                startActivity(SignUpActivityHelper.getIntent(this@SignMainActivity))
            }
        }
    }

    companion object {
        private const val TAG = "SignMainActivity"
    }
}