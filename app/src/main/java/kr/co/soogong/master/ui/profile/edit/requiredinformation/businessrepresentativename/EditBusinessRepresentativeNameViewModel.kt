package kr.co.soogong.master.ui.profile.edit.requiredinformation.businessrepresentativename

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.usecase.profile.GetBusinessRepresentativeNameUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveBusinessRepresentativeNameUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditBusinessRepresentativeNameViewModel @Inject constructor(
    private val getBusinessRepresentativeNameUseCase: GetBusinessRepresentativeNameUseCase,
    private val saveBusinessRepresentativeNameUseCase: SaveBusinessRepresentativeNameUseCase,
) : BaseViewModel() {
    val businessRepresentativeName = MutableLiveData("")


    fun getBusinessRepresentative() {
        Timber.tag(TAG).d("getBusinessRepresentative: ")
        viewModelScope.launch {
            getBusinessRepresentativeNameUseCase().let {
                businessRepresentativeName.postValue(it)
            }
        }
    }

    fun saveBusinessRepresentative() {
        Timber.tag(TAG).d("saveBusinessRepresentative: ")
        (!businessRepresentativeName.value.isNullOrEmpty()).let {
            saveBusinessRepresentativeNameUseCase(
                businessRepresentativeName.value!!
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_BUSINESS_REPRESENTATIVE_NAME_SUCCESSFULLY) },
                    onError = { setAction(SAVE_BUSINESS_REPRESENTATIVE_NAME_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditBusinessRepresentativeNameViewModel"
        const val SAVE_BUSINESS_REPRESENTATIVE_NAME_SUCCESSFULLY =
            "SAVE_BUSINESS_REPRESENTATIVE_NAME_SUCCESSFULLY"
        const val SAVE_BUSINESS_REPRESENTATIVE_NAME_FAILED =
            "SAVE_BUSINESS_REPRESENTATIVE_NAME_FAILED"
    }
}