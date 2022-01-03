package kr.co.soogong.master.ui.major.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.domain.usecase.major.GetCategoriesUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel() {
    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories: LiveData<List<Category>>
        get() = _categories

    init {
        requestCategories()
    }

    private fun requestCategories() {
        Timber.tag(TAG).d("getCategoryList: ")
        getCategoriesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getCategoryList successful: $it")
                    _categories.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).w("getCategoryList failed: $it")
                    setAction(GET_CATEGORY_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "CategoryViewModel"
        const val GET_CATEGORY_FAILED = "GET_CATEGORY_FAILED"
    }
}