package kr.co.soogong.master.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromFirebaseUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterUidInSharedUseCase
import kr.co.soogong.master.uihelper.auth.SignMainActivityHelper
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var getMasterIdFromFirebaseUseCase: GetMasterIdFromFirebaseUseCase

    @Inject
    lateinit var saveMasterUidInSharedUseCase: SaveMasterUidInSharedUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        checkSignIn()
    }

    private fun checkSignIn() {
        Timber.tag(TAG).d("checkSignIn: ")

        getMasterIdFromFirebaseUseCase()?.let { masterUid ->
            Timber.tag(TAG).d("masterId: $masterUid")

            saveMasterUidInSharedUseCase(masterUid)
            startActivity(MainActivityHelper.getIntent(this))
            return
        }

        Timber.tag(TAG).d("masterUid is null")
        startActivity(SignMainActivityHelper.getIntent(this))
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}