package kr.co.soogong.master.ui.profile.edit.otherflexibleoptions

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.profile.FlexibleCost
import kr.co.soogong.master.data.profile.OtherFlexibleOptions
import kr.co.soogong.master.domain.usecase.profile.GetFlexibleCostUseCase
import kr.co.soogong.master.domain.usecase.profile.GetOtherFlexibleOptionsUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveFlexibleCostUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveOtherFlexibleOptionsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditOtherFlexibleOptionsViewModel @Inject constructor(
    private val saveOtherFlexibleOptionsUseCase: SaveOtherFlexibleOptionsUseCase,
    private val getOtherFlexibleOptionsUseCase: GetOtherFlexibleOptionsUseCase
) : BaseViewModel() {
    val otherFlexibleOptions = ListLiveData<String>()

    fun getFlexibleCosts() {
        Timber.tag(TAG).d("getFlexibleCosts: ")
        viewModelScope.launch {
            otherFlexibleOptions.addAll(getOtherFlexibleOptionsUseCase().options)
        }
    }

    fun saveFlexibleCosts() {
        Timber.tag(TAG).d("saveFlexibleCosts: ")
        saveOtherFlexibleOptionsUseCase(
            OtherFlexibleOptions.NULL_OTHER_FLEXIBLE_OPTIONS
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {},
                onError = {}
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditOtherFlexibleOptionsViewModel"

    }
}