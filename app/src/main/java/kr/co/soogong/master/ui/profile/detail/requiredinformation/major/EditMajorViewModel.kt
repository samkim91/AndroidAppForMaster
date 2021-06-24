package kr.co.soogong.master.ui.profile.detail.requiredinformation.major

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.domain.usecase.profile.GetMajorUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMajorUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditMajorViewModel @Inject constructor(
    private val getMajorUseCase: GetMajorUseCase,
    private val saveMajorUseCase: SaveMajorUseCase,
) : BaseViewModel() {
    val major = ListLiveData<Major>()

    fun requestMajor() {
        Timber.tag(TAG).d("requestMajor: ")

        getMajorUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { major.addAll(it) },
                onError = { setAction(GET_MAJOR_FAILED) }
            ).addToDisposable()
    }

    fun saveMajor() {
        Timber.tag(TAG).d("saveMajor: ")
        major.value?.let {
            saveMajorUseCase(
                major = it
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_MAJOR_SUCCESSFULLY) },
                    onError = { setAction(SAVE_MAJOR_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditMajorViewModel"
        const val SAVE_MAJOR_SUCCESSFULLY = "SAVE_MAJOR_SUCCESSFULLY"
        const val SAVE_MAJOR_FAILED = "SAVE_MAJOR_FAILED"
        const val GET_MAJOR_FAILED = "GET_MAJOR_FAILED"
    }
}