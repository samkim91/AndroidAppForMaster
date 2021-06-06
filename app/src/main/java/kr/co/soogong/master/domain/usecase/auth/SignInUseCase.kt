package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.user.SignInInfo
import kr.co.soogong.master.network.AuthService
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val saveMasterKeyCodeUseCase: SaveMasterKeyCodeUseCase,
    private val saveMasterApprovalUseCase: SaveMasterApprovalUseCase,
    private val authService: AuthService,
    private val setAccessTokenUseCase: SaveAccessTokenUseCase,
    private val setRefreshTokenUseCase: SaveRefreshTokenUseCase,
) {
    operator fun invoke(phoneNumber: String?, password: String?): Single<SignInInfo> {
        if (BuildConfig.DEBUG) {
            saveMasterKeyCodeUseCase("1e73778811e69aa5")
//            setMasterKeyCodeUseCase("919dcdf215133b52")
            return Single.just(SignInInfo("", "", "", "", false))
        }

        return authService.login(phoneNumber, password)
            .doOnSuccess { signInInfo ->
                saveMasterKeyCodeUseCase(signInInfo.keycode)
                saveMasterApprovalUseCase(signInInfo.isApproved)
                //Todo.. token을 set하는 부분 추가
                setAccessTokenUseCase("todo")
                setRefreshTokenUseCase("todo")
           }

    }
}