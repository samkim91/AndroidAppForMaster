package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.user.SignInInfo
import kr.co.soogong.master.domain.usecase.SetAccessTokenUseCase
import kr.co.soogong.master.domain.usecase.SetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.SetMasterKeyCodeUseCase
import kr.co.soogong.master.domain.usecase.SetRefreshTokenUseCase
import kr.co.soogong.master.network.AuthService
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val setMasterKeyCodeUseCase: SetMasterKeyCodeUseCase,
    private val setMasterApprovalUseCase: SetMasterApprovalUseCase,
    private val authService: AuthService,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
) {
    operator fun invoke(phoneNumber: String?, password: String?): Single<SignInInfo> {
        if (BuildConfig.DEBUG) {
            setMasterKeyCodeUseCase("1e73778811e69aa5")
//            setMasterKeyCodeUseCase("919dcdf215133b52")
            return Single.just(SignInInfo("", "", "", "", false))
        }

        return authService.login(phoneNumber, password)
            .doOnSuccess { signInInfo ->
                setMasterKeyCodeUseCase(signInInfo.keycode)
                setMasterApprovalUseCase(signInInfo.isApproved)
                //Todo.. token을 set하는 부분 추가
                setAccessTokenUseCase("todo")
                setRefreshTokenUseCase("todo")
           }
    }
}