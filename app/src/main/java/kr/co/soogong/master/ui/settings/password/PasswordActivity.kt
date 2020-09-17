package kr.co.soogong.master.ui.settings.password

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityPasswordBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

class PasswordActivity : BaseActivity<ActivityPasswordBinding>(
    R.layout.activity_password
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
    }

    companion object {
        private const val TAG = "PasswordActivity"
    }
}