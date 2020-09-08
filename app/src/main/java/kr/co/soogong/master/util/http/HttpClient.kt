package kr.co.soogong.master.util.http

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import io.reactivex.Completable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.util.InjectHelper
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
    private const val URL = "https://api2.soogong.co.kr/"

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

        val httpCacheDirectory = File(InjectHelper.context?.cacheDir, "http")
        val cacheSize = 32 * 1024 * 1024L
        val networkFlipperPlugin = InjectHelper.networkFlipperPlugin
        val client = OkHttpClient.Builder()
            .cache(Cache(httpCacheDirectory, cacheSize))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(logInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
            .build()

        client.dispatcher.maxRequests = 16

        return client
    }

    fun getRequirementList(): Single<List<Requirement>> {
        return httpInterface.getRequirementList("d3899f668347aa1b")
            .map { list -> list.map { Requirement.from(it) } }
    }

    fun refuseRequirement(keycode: String): Completable {
        val data = HashMap<String, String>()
        data["branch_keycode"] = "d3899f668347aa1b"
        data["keycode"] = keycode
        return httpInterface.refuseRequirement(data)
    }

    fun sendMessage(
        keycode: String,
        price: String,
        contents: String,
        possibleDate: String
    ): Single<String> {
        val data = HashMap<String, String>()
        data["branch_keycode"] = "d3899f668347aa1b"
        data["keycode"] = keycode
        data["price"] = price
        data["contents"] = contents
        data["possible_date"] = possibleDate
        return httpInterface.sendMessage(data)
    }
}
