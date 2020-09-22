package kr.co.soogong.master.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.soogong.master.domain.AppSharedPreferenceHelper
import kr.co.soogong.master.ui.main.MainActivity
import kr.co.soogong.master.ui.sign.SignMainActivity
import kr.co.soogong.master.util.InjectHelper
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")

        val keyCode = getRepository(this).getString(AppSharedPreferenceHelper.BRANCH_KEYCODE, "")

        val intent: Intent = if (keyCode.isNullOrEmpty()) {
            Intent(this, SignMainActivity::class.java)
        } else {
            InjectHelper.keyCode = keyCode
            Intent(this, MainActivity::class.java)
        }

        startActivity(intent)

        finish()
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}