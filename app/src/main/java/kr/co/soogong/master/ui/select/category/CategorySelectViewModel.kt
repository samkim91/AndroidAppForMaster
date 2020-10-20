package kr.co.soogong.master.ui.select.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class CategorySelectViewModel : BaseViewModel() {
    private val _list = MutableLiveData<List<Category>>(emptyList())
    val list: LiveData<List<Category>>
        get() = _list

    init {
        getCategoryList()
    }

    private fun getCategoryList() {
        HttpClient.getCategoryList()
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