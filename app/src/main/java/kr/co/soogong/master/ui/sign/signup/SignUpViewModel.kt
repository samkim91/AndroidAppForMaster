package kr.co.soogong.master.ui.sign.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.soogong.master.ui.base.BaseViewModel

class SignUpViewModel : BaseViewModel() {
    private val _list = MutableLiveData<List<String>>()
    val list: LiveData<List<String>>
        get() = _list

    fun sendList(list: List<String>) {
        _list.postValue(list)
    }
}