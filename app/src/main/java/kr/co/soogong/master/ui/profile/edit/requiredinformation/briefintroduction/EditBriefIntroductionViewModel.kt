package kr.co.soogong.master.ui.profile.edit.requiredinformation.briefintroduction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.OtherFlexibleOptions
import kr.co.soogong.master.domain.usecase.profile.GetBriefIntroductionUseCase
import kr.co.soogong.master.domain.usecase.profile.GetOtherFlexibleOptionsUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveBriefIntroductionUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveOtherFlexibleOptionsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditBriefIntroductionViewModel @Inject constructor(
    private val getBriefIntroductionUseCase: GetBriefIntroductionUseCase,
    private val saveBriefIntroductionUseCase: SaveBriefIntroductionUseCase,
) : BaseViewModel() {
    val briefIntroduction = MutableLiveData("")

    fun getBriefIntro() {
        Timber.tag(TAG).d("getBriefIntro: ")
        viewModelScope.launch {
            briefIntroduction.value = getBriefIntroductionUseCase()
        }
    }

    fun saveBriefIntro() {
        Timber.tag(TAG).d("saveBriefIntro: ")
        briefIntroduction.value?.let {
            saveBriefIntroductionUseCase(
                briefIntroduction = it
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_BRIEF_INTRODUCTION_SUCCESSFULLY) },
                    onError = { setAction(SAVE_BRIEF_INTRODUCTION_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditBriefIntroductionViewModel"
        const val SAVE_BRIEF_INTRODUCTION_SUCCESSFULLY = "SAVE_BRIEF_INTRODUCTION_SUCCESSFULLY"
        const val SAVE_BRIEF_INTRODUCTION_FAILED = "SAVE_BRIEF_INTRODUCTION_FAILED"

    }
}