package kr.co.soogong.master

import android.app.Application
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

@HiltAndroidApp
open class SoogongApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val tagName = "[${getString(R.string.app_name)}-${BuildConfig.VERSION_NAME}]"
        Timber.plant(TimberLogTree(tagName))
        KakaoSdk.init(this, BuildConfig.KAKAO_KEY_CODE)
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_MAP_CLIENT_ID)
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.addPlugin(AWSApiPlugin())
        Amplify.addPlugin(AWSS3StoragePlugin())
        Amplify.configure(this)
        Timber.tag("App").d("onCreate: ")
        RxJavaPlugins.setErrorHandler {
            Timber.tag(getString(R.string.app_name)).w(it)
        }
    }
}

class TimberLogTree(
    private val tagName: String
) : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tagName, "$tag.$message", t)
    }
}
