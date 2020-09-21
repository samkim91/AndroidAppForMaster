package kr.co.soogong.master.util.http

import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.requirements.Estimate
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.util.InjectHelper
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
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
        okHttpClient = InjectHelper.getOkHttpClient()
        httpInterface = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(HttpInterface::class.java)
    }

    fun getRequirementList(keycode: String = "d3899f668347aa1b"): Single<List<Requirement>> {
        return httpInterface.getRequirementList(keycode)
            .map { list -> list.map { Requirement.fromReceived(it) } }
    }

    fun getProgressList(keycode: String = "d3899f668347aa1b"): Single<List<Requirement>> {
        return httpInterface.getProgressList(keycode)
            .map { list -> list.map { Requirement.fromProgress(it) } }
    }

    fun refuseRequirement(
        branchKeycode: String = "d3899f668347aa1b",
        keycode: String
    ): Single<Response> {
        val data = HashMap<String, String>()
        data["branch_keycode"] = branchKeycode
        data["keycode"] = keycode
        return httpInterface.refuseRequirement(data)
    }

    fun sendMessage(
        branchKeycode: String = "d3899f668347aa1b",
        keycode: String,
        estimate: Estimate
    ): Single<String> {
        val data = HashMap<String, String?>()
        data["branch_keycode"] = branchKeycode
        data["keycode"] = keycode
        data["price"] = estimate.price
        data["contents"] = estimate.contents
        data["possible_date"] = estimate.possibleDate
        return httpInterface.sendMessage(data)
    }

    fun updateFCMToken(keycode: String = "d3899f668347aa1b", fcmKey: String): Single<Response> {
        val data = HashMap<String, String>()
        data["keycode"] = keycode
        data["key"] = fcmKey

        return httpInterface.updateFCMToken(data)
    }

    fun getUserProfile(keycode: String = "d3899f668347aa1b"): Single<User> {
        return httpInterface.getUserProfile(keycode).map {
            Timber.tag(TAG).d("getUserProfile: $it")
            User.from(it)
        }
    }
}
