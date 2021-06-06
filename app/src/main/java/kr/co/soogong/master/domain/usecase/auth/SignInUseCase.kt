package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.user.SignInInfo
import kr.co.soogong.master.network.AuthService
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val setMasterKeyCodeUseCase: SetMasterKeyCodeUseCase,
    private val setMasterApprovalUseCase: SetMasterApprovalUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val authService: AuthService
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
                // Todo.. 토큰 저장
                saveAccessTokenUseCase("accessToken")
                saveRefreshTokenUseCase("refreshToken")
            }
    }
}