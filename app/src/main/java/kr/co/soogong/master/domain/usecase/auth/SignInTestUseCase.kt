package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.dto.auth.ResponseSignInDto
import kr.co.soogong.master.network.auth.AuthService
import javax.inject.Inject

@Reusable
class SignInTestUseCase @Inject constructor(
    private val saveMasterIdInSharedUseCase: SaveMasterIdInSharedUseCase,
    private val saveMasterApprovalUseCase: SaveMasterApprovalUseCase,
    private val authService: AuthService,
    private val setAccessTokenUseCase: SaveAccessTokenUseCase,
    private val setRefreshTokenUseCase: SaveRefreshTokenUseCase,
) {
    operator fun invoke(phoneNumber: String?, password: String?): Single<ResponseSignInDto> {
        saveMasterIdInSharedUseCase("1e73778811e69aa5")
//            setMasterKeyCodeUseCase("919dcdf215133b52")
        return Single.just(ResponseSignInDto("", "", "", "", false))

//        return authService.login(phoneNumber, password)
//            .doOnSuccess { responseSignIn ->
//                saveMasterIdInSharedUseCase(responseSignIn.keycode)
//                saveMasterApprovalUseCase(responseSignIn.isApproved)
//                //Todo.. token을 set하는 부분 추가
//                setAccessTokenUseCase("todo")
//                setRefreshTokenUseCase("todo")
//            }
    }
}