package kr.co.soogong.master.util

import android.content.Context
import kr.co.soogong.master.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

object InjectHelper {
    private var weakReferenceContext: WeakReference<Context?>? = null

    var context: Context?
        get() = weakReferenceContext?.get()
        @Synchronized
        set(context) {
            weakReferenceContext = WeakReference(context)
        }

    private var weakReferenceInterceptor: WeakReference<Interceptor?>? = null

    var interceptor: Interceptor?
        get() = weakReferenceInterceptor?.get()
        @Synchronized
        set(context) {
            weakReferenceInterceptor = WeakReference(context)
        }

    fun getOkHttpClient(): OkHttpClient {
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

        val httpCacheDirectory = File(context?.cacheDir, "http")
        val cacheSize = 32 * 1024 * 1024L
        val networkFlipperPlugin = interceptor
        val client = OkHttpClient.Builder()
            .cache(Cache(httpCacheDirectory, cacheSize))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(logInterceptor)
            .addInterceptor(authInterceptor)

        networkFlipperPlugin?.let {
            client.addInterceptor(it)
        }

        val okHttpClient = client.build()

        okHttpClient.dispatcher.maxRequests = 16

        return okHttpClient
    }
}