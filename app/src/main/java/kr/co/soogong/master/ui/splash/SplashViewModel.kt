package kr.co.soogong.master.ui.splash

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.domain.usecase.auth.GetVersionUseCase
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateDirectRepairYnUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
    private val updateDirectRepairYnUseCase: UpdateDirectRepairYnUseCase,
    private val getVersionUseCase: GetVersionUseCase,
) : BaseViewModel() {

    private val _masterSimpleInfo = MutableLiveData<MasterDto>()

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

    fun requestMasterSimpleInfo() {
        Timber.tag(TAG).d("requestMasterSimpleInfo: ")

        getMasterSimpleInfoUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo successful: $it")
                    _masterSimpleInfo.value = it
                    sendEvent(GET_MASTER_DIRECT_REPAIR, it.directRepairYn!!)
                },
                onError = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun updateDirectRepairYn(directRepairYn: Boolean) {
        Timber.tag(TAG).d("updateDirectRepairYn to: $directRepairYn")

        updateDirectRepairYnUseCase(
            MasterDto(
                id = _masterSimpleInfo.value?.id,
                uid = _masterSimpleInfo.value?.uid,
                directRepairYn = directRepairYn)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("updateDirectRepairYn successful: $it")
                    sendEvent(UPDATE_DIRECT_REPAIR_SUCCESSFULLY, it.directRepairYn!!)
                },
                onError = {
                    Timber.tag(TAG).d("updateDirectRepairYn failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "MainViewModel"
        const val GET_VERSION_SUCCESSFULLY = "GET_VERSION_SUCCESSFULLY"
        const val GET_MASTER_UID_SUCCESSFULLY = "GET_MASTER_UID_SUCCESSFULLY"
        const val GET_MASTER_UID_FAILED = "GET_MASTER_UID_FAILED"
        const val GET_MASTER_DIRECT_REPAIR = "GET_MASTER_DIRECT_REPAIR"
        const val UPDATE_DIRECT_REPAIR_SUCCESSFULLY = "UPDATE_DIRECT_REPAIR_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}