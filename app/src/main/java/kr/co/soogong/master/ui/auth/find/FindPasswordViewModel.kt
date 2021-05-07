package kr.co.soogong.master.ui.auth.find

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.auth.FindPasswordUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val findPasswordUseCase: FindPasswordUseCase
) : BaseViewModel() {
    val email = MutableLiveData<String>()

    fun findInfo() {
        Timber.tag(TAG).d("findInfo: ")
        findPasswordUseCase(email.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("findInfo: $it")
                success()
            }, {
                Timber.tag(TAG).w("findInfo: $it")
                fail()
            })
            .addToDisposable()
    }

    private fun fail() {
        setAction(FAIL)
    }

    private fun success() {
        setAction(SUCCESS)
    }

    companion object {
        private const val TAG = "FindInfoViewModel"
        const val FAIL = "FAIL"
        const val SUCCESS = "SUCCESS"
    }
}