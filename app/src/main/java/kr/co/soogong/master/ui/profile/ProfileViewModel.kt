package kr.co.soogong.master.ui.profile

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class ProfileViewModel(private val repository: Repository) : BaseViewModel() {
    fun requestUserProfile() {
        HttpClient.getUserProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("requestUserProfile: $it")
            }, {
                Timber.tag(TAG).e("requestUserProfile: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }

}