package kr.co.soogong.master.ui.profile.edit.requiredinformation.briefintroduction

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.profile.OtherFlexibleOptions
import kr.co.soogong.master.domain.usecase.profile.GetOtherFlexibleOptionsUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveOtherFlexibleOptionsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditBriefIntroductionViewModel @Inject constructor(
    private val saveOtherFlexibleOptionsUseCase: SaveOtherFlexibleOptionsUseCase,
    private val getOtherFlexibleOptionsUseCase: GetOtherFlexibleOptionsUseCase
) : BaseViewModel() {
    val briefIntroduction = MutableLiveData("")
    
    fun getBriefIntro() {
        Timber.tag(TAG).d("getBriefIntro: ")

    }

    fun saveBriefIntro() {
        Timber.tag(TAG).d("saveBriefIntro: ")
        saveOtherFlexibleOptionsUseCase(
            OtherFlexibleOptions.TEST_OTHER_FLEXIBLE_OPTIONS
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {},
                onError = {}
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditBriefIntroductionViewModel"

    }
}