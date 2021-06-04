package kr.co.soogong.master.network

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import kr.co.soogong.master.contract.AppSharedPreferenceContract
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
    private val sharedPreferences: SharedPreferences,
    private val authService: AuthService,
) : Authenticator {
    private val newToken = MutableLiveData("")

    override fun authenticate(route: Route?, response: Response): Request? {
        // Todo.. 무한루프에 빠지지 않게 하려면 어떤 조건을 걸어야하는지 검토 필요
        if (response.request.header("Authorization") != null ||
            response.request.header("Authorization") != "Bearer " +
            "${sharedPreferences.getString(AppSharedPreferenceContract.JWT_ACCESS, "")}"
        ) {
            // refresh failed 일 때, 무한 루프에서 벗어나기 위함
            return null
        }

        val refreshToken = sharedPreferences.getString(AppSharedPreferenceContract.JWT_REFRESH, "")
        refreshToken?.let { refreshToken ->
            authService.resignIn(refreshToken)
                .doOnSuccess { responseJson ->
                    newToken.value = responseJson.body.getAsJsonObject("newToken").asString
                    sharedPreferences.edit()
                        .putString(AppSharedPreferenceContract.JWT_ACCESS, newToken.value).apply()
                }
        }

        return response
            .request
            .newBuilder()
            .header("Authorization", "Bearer ${newToken.value}")
            .build()
    }
}


