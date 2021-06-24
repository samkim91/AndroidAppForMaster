package kr.co.soogong.master.ui.profile.detail.requiredinformation.ownername

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.profile.GetOwnerNameUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveOwnerNameUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditOwnerNameViewModel @Inject constructor(
    private val getOwnerNameUseCase: GetOwnerNameUseCase,
    private val saveOwnerNameUseCase: SaveOwnerNameUseCase,
) : BaseViewModel() {
    val ownerName = MutableLiveData("")

    fun requestOwnerName() {
        Timber.tag(TAG).d("requestOwnerName: ")

        getOwnerNameUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { response ->
                    ownerName.value = response
                },
                onError = { setAction(GET_OWNER_NAME_FAILED) }
            ).addToDisposable()
    }

    fun saveOwnerName() {
        Timber.tag(TAG).d("saveOwnerName: ")
        (!ownerName.value.isNullOrEmpty()).let {
            saveOwnerNameUseCase(
                ownerName.value!!
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_OWNER_NAME_SUCCESSFULLY) },
                    onError = { setAction(SAVE_OWNER_NAME_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditOwnerNameViewModel"
        const val SAVE_OWNER_NAME_SUCCESSFULLY = "SAVE_OWNER_NAME_SUCCESSFULLY"
        const val SAVE_OWNER_NAME_FAILED = "SAVE_OWNER_NAME_FAILED"
        const val GET_OWNER_NAME_FAILED = "GET_OWNER_NAME_FAILED"
    }
}