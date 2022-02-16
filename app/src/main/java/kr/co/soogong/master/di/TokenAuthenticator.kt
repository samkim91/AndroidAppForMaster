package kr.co.soogong.master.di

import androidx.lifecycle.MutableLiveData
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

// 메인참고 : https://medium.com/@theanilpaudel/using-the-power-of-retrofit-okhttp-and-dagger-2-for-jwt-token-authentication-ad8db6121eac
// 보조참고 : https://akaisun.tistory.com/73
@Singleton
class TokenAuthenticator @Inject constructor() : Authenticator {
    private val newToken = MutableLiveData("")

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization") != null ||
            response.request.header("Authorization") != "Bearer "
//            "${getAccessTokenUseCase()}"
        ) {
            // refresh failed 일 때, 무한 루프에서 벗어나기 위함
            return null
        }

//        getRefreshTokenUseCase()?.let {
        // refreshToken ->
//            authService.get().resignIn(refreshToken)
//                .doOnSuccess { responseJson ->
//                    newToken.value = responseJson.body.getAsJsonObject("newToken").asString
//                    saveAccessTokenUseCase(newToken.value)
//                    saveRefreshTokenUseCase(newToken.value)
//                }
//        }

        return response
            .request
            .newBuilder()
            .header("Authorization", "Bearer ${newToken.value}")
            .build()
    }
}


