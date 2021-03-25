package kr.co.soogong.master.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.uiinterface.main.MainActivityHelper
import kr.co.soogong.master.uiinterface.sign.SignMainActivityHelper
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        checkSignIng()
    }

    private fun checkSignIng() {
        val keyCode = getMasterKeyCodeUseCase()
        val intent = if (keyCode.isNullOrEmpty()) {
            SignMainActivityHelper.getIntent(this)
        } else {
            MainActivityHelper.getIntent(this)
        }
        startActivity(intent)
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}