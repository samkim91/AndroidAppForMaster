package kr.co.soogong.master.network

import androidx.lifecycle.MutableLiveData
import dagger.Lazy
import kr.co.soogong.master.domain.usecase.auth.GetAccessTokenUseCase
import kr.co.soogong.master.domain.usecase.auth.GetRefreshTokenUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveAccessTokenUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveRefreshTokenUseCase
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

// 메인참고 : https://medium.com/@theanilpaudel/using-the-power-of-retrofit-okhttp-and-dagger-2-for-jwt-token-authentication-ad8db6121eac
// 보조참고 : https://akaisun.tistory.com/73
@Singleton
class TokenAuthenticator @Inject constructor(
    private val authService: Lazy<AuthService>,     // dagger cycle dependency error를 막기 위해 dagger.lazy로 선언
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
) : Authenticator {
    private val newToken = MutableLiveData("")

    override fun authenticate(route: Route?, response: Response): Request? {
        // Todo.. 무한루프에 빠지지 않게 하려면 어떤 조건을 걸어야하는지 검토 필요
        if (response.request.header("Authorization") != null ||
            response.request.header("Authorization") != "Bearer " +
            "${getAccessTokenUseCase()}"
        ) {
            // refresh failed 일 때, 무한 루프에서 벗어나기 위함
            return null
        }

        getRefreshTokenUseCase()?.let { refreshToken ->
            authService.get().resignIn(refreshToken)
                .doOnSuccess { responseJson ->
                    newToken.value = responseJson.body.getAsJsonObject("newToken").asString
                    saveAccessTokenUseCase(newToken.value)
                    saveRefreshTokenUseCase(newToken.value) // Todo.. responsed에서 token 가져와서 set 추가 작업
                }
        }

        return response
            .request
            .newBuilder()
            .header("Authorization", "Bearer ${newToken.value}")
            .build()
    }
}


