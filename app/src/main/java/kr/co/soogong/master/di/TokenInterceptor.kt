package kr.co.soogong.master.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()

        // 토큰이 아직 발급되지 않았을 요청에 대해 예외처리

//        if (originRequest.url.encodedPath.contains("/signin") && originRequest.method == "post"
//            || originRequest.url.encodedPath.contains("/signup") && originRequest.method == "post"
//        ) return chain.proceed(originRequest)

        return chain.proceed(originRequest)

//        return chain.proceed(
//            getAccessTokenUseCase()?.let { accessToken ->
//                originRequest.newBuilder()
//                    .addHeader("Authorization", "Bearer $accessToken")
////                    .url(originRequest.url)   // 필요한지 모르겠음..?
//                    .build()
//            }!!
//        )
    }
}

//when(response.code) {
//    500 -> {
//        context.toast("내부 서버 에러가 발생하였습니다. 수공에 연락해주세요.")
//    }
//    404 -> {
//        context.toast("해당 URL을 찾을 수 없습니다.")
//    }
//}


