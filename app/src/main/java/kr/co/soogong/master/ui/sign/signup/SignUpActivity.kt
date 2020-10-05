package kr.co.soogong.master.ui.sign.signup

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySignUpBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(
    R.layout.activity_sign_up
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@SignUpActivity
        }
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}