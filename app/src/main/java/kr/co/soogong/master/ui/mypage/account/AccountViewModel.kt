package kr.co.soogong.master.ui.mypage.account

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val httpClient: HttpClient
) : BaseViewModel() {
    fun withDraw() {
        // 탈퇴 진행
    }
}