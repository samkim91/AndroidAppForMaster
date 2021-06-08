package kr.co.soogong.master.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromFirebaseUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterIdInSharedUseCase
import kr.co.soogong.master.uiinterface.auth.SignMainActivityHelper
import kr.co.soogong.master.uiinterface.main.MainActivityHelper
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var getMasterIdFromFirebaseUseCase: GetMasterIdFromFirebaseUseCase

    @Inject
    lateinit var saveMasterIdInSharedUseCase: SaveMasterIdInSharedUseCase

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
        Timber.tag(TAG).d("checkSignIng: ")

        getMasterIdFromFirebaseUseCase()?.let { masterId ->
            Timber.tag(TAG).d("masterId: $masterId")

            saveMasterIdInSharedUseCase(masterId)
            startActivity(MainActivityHelper.getIntent(this))
            return
        }

        Timber.tag(TAG).d("masterId is null")
        startActivity(SignMainActivityHelper.getIntent(this))
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}