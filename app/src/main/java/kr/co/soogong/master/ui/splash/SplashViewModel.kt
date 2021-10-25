package kr.co.soogong.master.ui.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.domain.usecase.auth.GetVersionUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val getVersionUseCase: GetVersionUseCase,
) : BaseViewModel() {

    fun checkLatestVersion() {
        Timber.tag(TAG).d("checkLatestVersion: ")

        getVersionUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getLatestVersion success: $it")
                    sendEvent(GET_VERSION_SUCCESSFULLY, it)
                },
                onError = {
                    Timber.tag(TAG).d("getLatestVersion failed: $it")
                }
            ).addToDisposable()
    }

    fun checkSignIn() {
        Timber.tag(TAG).d("checkSignIn: ")

        getMasterUidFromSharedUseCase().let { masterUid ->
            Timber.tag(TAG).d("master Uid: $masterUid")
            if (masterUid.isNullOrEmpty()) setAction(GET_MASTER_UID_FAILED)
            else setAction(GET_MASTER_UID_SUCCESSFULLY)
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
        const val GET_VERSION_SUCCESSFULLY = "GET_VERSION_SUCCESSFULLY"
        const val GET_MASTER_UID_SUCCESSFULLY = "GET_MASTER_UID_SUCCESSFULLY"
        const val GET_MASTER_UID_FAILED = "GET_MASTER_UID_FAILED"
    }
}