package kr.co.soogong.master.ui.requirements.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import java.util.*

class ReceivedViewModel(private val repository: Repository) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _requirementList: LiveData<List<ReceivedCard>> =
        repository.getRequirementList().map { list ->
            if (list.isNullOrEmpty()) {
                _emptyList.value = true
                return@map emptyList<ReceivedCard>()
            } else {
                _emptyList.value = false
                return@map list.map { ReceivedCard.from(it) }
            }
        }

    val requirementList: LiveData<List<ReceivedCard>>
        get() = _requirementList


    fun test() {
        val temp = Requirement(
            id = "1",
            category = "욕실장",
            location = "서울시 관악구",
            date = Date(15689871),
            userName = "홍길동",
            content = "욕실장이 벽에서 떨어졌어요. 새로 욕실장을 설치했으면 좋겠습니다.",
            houseType = "아파트",
            size = "10~20평",
            image = "",
            status = "정상"
        )

        viewModelScope.launch {
            repository.insert(temp)
        }
    }

    companion object {
        private const val TAG = "ProgressViewModel"
    }
}