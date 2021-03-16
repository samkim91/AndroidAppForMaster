package kr.co.soogong.master.ui.select.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategorySelectViewModel @Inject constructor(
    private val httpClient: HttpClient
) : BaseViewModel() {
    private val _list = MutableLiveData<List<Category>>(emptyList())
    val list: LiveData<List<Category>>
        get() = _list

    init {
        getCategoryList()
    }

    private fun getCategoryList() {
        httpClient.getCategoryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("getCategoryList: $it")
                _list.postValue(it)
            }, {
                Timber.tag(TAG).w("getCategoryList: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "CategoryViewModel"
    }
}