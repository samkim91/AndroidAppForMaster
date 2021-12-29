package kr.co.soogong.master.ui.major.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.CategoryDto
import kr.co.soogong.master.domain.usecase.major.GetCategoryListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
) : BaseViewModel() {
    private val _list = MutableLiveData<List<CategoryDto>>(emptyList())
    val list: LiveData<List<CategoryDto>>
        get() = _list

    init {
        getCategoryList()
    }

    private fun getCategoryList() {
        Timber.tag(TAG).d("getCategoryList: ")
        getCategoryListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getCategoryList: $it")
                    _list.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).w("getCategoryList: $it")
                    setAction(GET_CATEGORY_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "CategoryViewModel"
        const val GET_CATEGORY_FAILED = "GET_CATEGORY_FAILED"
    }
}