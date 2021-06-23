package kr.co.soogong.master.ui.profile.detail.requiredinformation.introduction

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.profile.GetIntroductionUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveIntroductionUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditIntroductionViewModel @Inject constructor(
    private val getIntroductionUseCase: GetIntroductionUseCase,
    private val saveIntroductionUseCase: SaveIntroductionUseCase,
) : BaseViewModel() {
    val introduction = MutableLiveData("")

    fun requestIntroduction() {
        Timber.tag(TAG).d("requestIntroduction: ")

        getIntroductionUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { response ->
                    introduction.value = response
                },
                onError = {
                    setAction(GET_INTRODUCTION_FAILED)
                }
            ).addToDisposable()
    }

    fun saveIntroduction() {
        Timber.tag(TAG).d("saveIntroduction: ")
        introduction.value?.let {
            saveIntroductionUseCase(
                briefIntroduction = it
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_INTRODUCTION_SUCCESSFULLY) },
                    onError = { setAction(SAVE_INTRODUCTION_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditIntroductionViewModel"
        const val SAVE_INTRODUCTION_SUCCESSFULLY = "SAVE_INTRODUCTION_SUCCESSFULLY"
        const val SAVE_INTRODUCTION_FAILED = "SAVE_INTRODUCTION_FAILED"
        const val GET_INTRODUCTION_FAILED = "GET_INTRODUCTION_FAILED"

    }
}