package kr.co.soogong.master.network

import android.content.SharedPreferences
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = sharedPreferences.getString(AppSharedPreferenceContract.ACCESS_TOKEN, "")
        val originRequest = chain.request()

        // Todo.. 토큰이 발급되지 않은 상태에서 요청을 하는 경우를 찾아서 다시 넣어줘야함.
        // 토큰이 아직 발급되지 않았을 요청에 대해 예외처리
        if (originRequest.url.encodedPath.contains("/signin") && originRequest.method == "post"
            || originRequest.url.encodedPath.contains("/signup") && originRequest.method == "post"
        ) return chain.proceed(originRequest)

        return chain.proceed(
            accessToken?.let { accessToken ->
                originRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
//                    .url(originRequest.url)   // 필요한지 모르겠음..?
                    .build()
            }!!
        )
    }
}


