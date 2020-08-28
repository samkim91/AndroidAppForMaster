package kr.co.soogong.master.util.http

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import io.reactivex.Flowable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.util.ContextHelper
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object HttpClient {
    private val TAG = HttpClient::class.java.simpleName
    private const val URL = "{Server URI}" // TODO

    private lateinit var instance: HttpClient
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var httpInterface: HttpInterface

    init {
        updateHttpClient()
    }

    fun newInstance(): HttpClient {
        if (!::instance.isInitialized) {
            instance = HttpClient
        }
        return instance
    }

    private fun updateHttpClient() {
        okHttpClient = getOkHttpClient()
        httpInterface = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(HttpInterface::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val authInterceptor = Interceptor { chain ->
            val newUrl = chain.request().url
                .newBuilder()
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }

        val httpCacheDirectory = File(ContextHelper.context?.cacheDir, "http")
        val cacheSize = 32 * 1024 * 1024L

        val client = OkHttpClient.Builder()
            .cache(Cache(httpCacheDirectory, cacheSize))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(logInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(FlipperOkhttpInterceptor(NetworkFlipperPlugin()))
            .build()

        client.dispatcher.maxRequests = 16

        return client
    }

    fun getPopularMovie(
        language: String = "ko-KR"
    ): Flowable<String> {
        return httpInterface.getPopularMovie(language)
    }
}
