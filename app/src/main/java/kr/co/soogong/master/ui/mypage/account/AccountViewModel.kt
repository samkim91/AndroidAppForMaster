package kr.co.soogong.master.ui.mypage.account

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.network.AuthService
import kr.co.soogong.master.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authService: AuthService
) : BaseViewModel() {
    fun withDraw() {
        // 탈퇴 진행
    }
}