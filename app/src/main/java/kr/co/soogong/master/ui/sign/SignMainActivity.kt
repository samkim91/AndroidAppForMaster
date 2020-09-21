package kr.co.soogong.master.ui.sign

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySignMainBinding
import kr.co.soogong.master.ui.base.BaseActivity
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
            setSignInClick {
                Timber.tag(TAG).d("initLayout: SignInClick")
            }

            setSignUpClick {
                Timber.tag(TAG).d("initLayout: SignUpClick")
            }
        }
    }

    companion object {
        private const val TAG = "SignMainActivity"
    }
}