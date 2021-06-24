package kr.co.soogong.master.ui.profile.detail.otherflexibleoptions

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.profile.OtherFlexibleOptions
import kr.co.soogong.master.domain.usecase.profile.GetOtherFlexibleOptionsUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveOtherFlexibleOptionsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditOtherFlexibleOptionsViewModel @Inject constructor(
    private val saveOtherFlexibleOptionsUseCase: SaveOtherFlexibleOptionsUseCase,
    private val getOtherFlexibleOptionsUseCase: GetOtherFlexibleOptionsUseCase
) : BaseViewModel() {
    val otherFlexibleOptions = ListLiveData<String>()

    fun requestOtherFlexibleOptions() {
        Timber.tag(TAG).d("requestOtherFlexibleOptions: ")
        getOtherFlexibleOptionsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    otherFlexibleOptions.addAll(it.options)
                },
                onError = { setAction(GET_OTHER_FLEXIBLE_OPTIONS_FAILED) }
            ).addToDisposable()
    }

    fun saveOtherFlexibleOptions() {
        Timber.tag(TAG).d("saveOtherFlexibleOptions: ")

        otherFlexibleOptions.value?.let {
            saveOtherFlexibleOptionsUseCase(
                OtherFlexibleOptions(
                    it
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_OTHER_FLEXIBLE_OPTIONS_SUCCESSFULLY) },
                    onError = { setAction(SAVE_OTHER_FLEXIBLE_OPTIONS_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditOtherFlexibleOptionsViewModel"

        const val SAVE_OTHER_FLEXIBLE_OPTIONS_SUCCESSFULLY =
            "SAVE_OTHER_FLEXIBLE_OPTIONS_SUCCESSFULLY"
        const val SAVE_OTHER_FLEXIBLE_OPTIONS_FAILED = "SAVE_OTHER_FLEXIBLE_OPTIONS"
        const val GET_OTHER_FLEXIBLE_OPTIONS_FAILED = "GET_OTHER_FLEXIBLE_OPTIONS_FAILED"
    }
}