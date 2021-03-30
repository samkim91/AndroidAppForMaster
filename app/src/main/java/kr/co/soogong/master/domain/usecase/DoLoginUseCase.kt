package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.network.AuthService
import javax.inject.Inject

@Reusable
class DoLoginUseCase @Inject constructor(
    private val setMasterKeyCodeUseCase: SetMasterKeyCodeUseCase,
    private val authService: AuthService
) {
    operator fun invoke(email: String?, password: String?): Single<String> {
        if (BuildConfig.DEBUG) {
            setMasterKeyCodeUseCase("919dcdf215133b52")
            return Single.just("DEBUG")
        }

        return authService.login(email, password)
            .doOnSuccess { masterKey ->
                setMasterKeyCodeUseCase(masterKey)
            }
    }
}