package kr.co.soogong.master.ui.common

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class EndlessScrollableViewModel @Inject constructor(

) : BaseViewModel() {

    val pageSize: Int = 10             // 한 번의 로딩에 몇 개의 데이터를 가져올지 정하는 값
    private val visibleThreshold: Int = 3      // 마지막 아이템에 도달하기 전에 데이터 로딩을 하기 위한 값

    var offset: Int = 0             // 서버로 보낼 데이터 시작 위치
    var loading: Boolean = true     // 로딩 중이면, 스크롤 이벤트에 데이터 요청이 더 이상 발생하지 않도록 하기 위한 플래그
    var totalItemCount: Int = 0     // 총 아이템 갯수를 통해, 더 로드가 필요한지 확인하기 위함
    var previousTotalItemCount = 0  // loading 플래그를 false 로 바꿀 시점을 찾기 위한 값
    var last: Boolean = false       // 마지막 페이지인지 알 수 있는 플래그

    fun onScroll(linearLayoutManager: LinearLayoutManager) {
        // 총 아이템 개수와 리사이클러뷰 화면의 마지막 뷰 위치를 구함
        val lastVisibleItemPosition =
            linearLayoutManager.findLastVisibleItemPosition()     // findLastCompletelyVisibleItemPosition() 도 고려 필요

//        Timber.tag(TAG).d("onScroll: $loading, $totalItemCount, $previousTotalItemCount, $last, $lastVisibleItemPosition")

        // 로딩중이라는 것은 api request 를 요청한 상태이고, response 를 통해 totalItemCount 가 증가해야 다음 request 를 보낼 수 있다.
        if (loading && totalItemCount > previousTotalItemCount) {
            Timber.tag(TAG).d("1st Case:")
            previousTotalItemCount = totalItemCount
            loading = false
        }

        // 로딩중이 아니고, 마지막 페이지도 아니고, 총 아이템 개수가 화면의 마지막 아이템에 가까워졌으므로, 새로운 데이터를 받아와야한다.
        if (!loading && !last && (totalItemCount < lastVisibleItemPosition + visibleThreshold)) {
            Timber.tag(TAG).d("2nd Case:")
            offset += pageSize
            loadMoreItems()
            loading = true
        }
    }

    // 초기화
    fun resetState() {
        Timber.tag(TAG).d("resetState: ")
        offset = 0
        loading = true
        totalItemCount = 0
        previousTotalItemCount = 0
        last = false
    }

    // 아이템을 더 불러오는 메소드. 실제 아이템을 불러올 코드에서 사용
    open fun loadMoreItems() {}

    companion object {
        const val TAG = "EndlessScrollableViewModel"
    }
}