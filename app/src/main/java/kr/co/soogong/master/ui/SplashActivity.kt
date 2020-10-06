package kr.co.soogong.master.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.soogong.master.domain.AppSharedPreferenceHelper
import kr.co.soogong.master.uiinterface.main.MainActivityHelper
import kr.co.soogong.master.uiinterface.sign.SignMainActivityHelper
import kr.co.soogong.master.util.InjectHelper
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        checkSignIng()
    }

    private fun checkSignIng() {
        val keyCode = getRepository(this).getString(AppSharedPreferenceHelper.BRANCH_KEYCODE, "")

        val intent = if (keyCode.isNullOrEmpty()) {
            SignMainActivityHelper.getIntent(this)
        } else {
            InjectHelper.keyCode = keyCode
            MainActivityHelper.getIntent(this)
        }

        startActivity(intent)

        finish()
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}