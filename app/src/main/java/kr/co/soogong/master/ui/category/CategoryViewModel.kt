package kr.co.soogong.master.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class CategoryViewModel : BaseViewModel() {
    private val _list = MutableLiveData<List<String>>(emptyList())
    val list: LiveData<List<String>>
        get() = _list

    fun getCategoryList() {
        HttpClient.getCategories()
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