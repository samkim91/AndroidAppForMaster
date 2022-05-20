package kr.co.soogong.master.presentation.ui.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.domain.usecase.preferences.GetVersionUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val getVersionUseCase: GetVersionUseCase,
) : BaseViewModel() {

    // 최신 버전 확인
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
                    setAction(GET_VERSION_INFO_FAILED)
                }
            ).addToDisposable()
    }

    // 로그인 여부 확인
    fun checkSignIn() {
        Timber.tag(TAG).d("checkSignIn: ")

        getMasterUidFromSharedUseCase().let { masterUid ->
            Timber.tag(TAG).d("master Uid: $masterUid")
            setAction(if (masterUid.isNotEmpty()) GET_MASTER_UID_SUCCESSFULLY else GET_MASTER_UID_RETURN_NULL)
        }
    }

    companion object {
        private val TAG = SplashViewModel::class.java.simpleName
        const val GET_VERSION_SUCCESSFULLY = "GET_VERSION_SUCCESSFULLY"
        const val GET_VERSION_INFO_FAILED = "GET_VERSION_INFO_FAILED"

        const val GET_MASTER_UID_SUCCESSFULLY = "GET_MASTER_UID_SUCCESSFULLY"
        const val GET_MASTER_UID_RETURN_NULL = "GET_MASTER_UID_RETURN_NULL"
    }
}