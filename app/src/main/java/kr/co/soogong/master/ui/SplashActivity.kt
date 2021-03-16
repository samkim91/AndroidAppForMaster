package kr.co.soogong.master.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.domain.AppSharedPreferenceHelper
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.uiinterface.main.MainActivityHelper
import kr.co.soogong.master.uiinterface.sign.SignMainActivityHelper
import kr.co.soogong.master.util.InjectHelper
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        checkSignIng()
    }

    private fun checkSignIng() {
        val keyCode = repository.getString(AppSharedPreferenceHelper.BRANCH_KEYCODE)

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