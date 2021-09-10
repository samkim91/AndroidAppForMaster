package kr.co.soogong.master.ui.requirement.action.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : BaseViewModel() {

    private val _spinnerItems = listOf("7일", "30일", "90일", "전체")
    val spinnerItems: List<String>
        get() = _spinnerItems

    val selectedItems = MutableLiveData(_spinnerItems[0])

    companion object {
        private const val TAG = "SearchViewModel"
    }
}