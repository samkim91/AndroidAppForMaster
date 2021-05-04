package kr.co.soogong.master.domain.usecase

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.user.SignInInfo
import kr.co.soogong.master.network.AuthService
import java.util.*
import javax.inject.Inject

@Reusable
class DoLoginUseCase @Inject constructor(
    private val setMasterKeyCodeUseCase: SetMasterKeyCodeUseCase,
    private val authService: AuthService
) {
    operator fun invoke(phoneNumber: String?, password: String?): Single<SignInInfo> {
        if (BuildConfig.DEBUG) {
            setMasterKeyCodeUseCase("1e73778811e69aa5", false)
//            setMasterKeyCodeUseCase("919dcdf215133b52")
            return Single.just(null)
        }

        return authService.login(phoneNumber, password)
            .doOnSuccess { signInInfo ->
                setMasterKeyCodeUseCase(signInInfo.keycode, signInInfo.isApproved)
            }
    }
}