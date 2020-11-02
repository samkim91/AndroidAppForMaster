package kr.co.soogong.master

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import io.reactivex.plugins.RxJavaPlugins
import kr.co.soogong.master.util.InjectHelper
import timber.log.Timber

open class SoogongApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(TimberLogTree())
        InjectHelper.context = applicationContext
        KakaoSdk.init(this, BuildConfig.KAKAO_KEY_CODE)
        Timber.tag("App").d("onCreate: ")

        RxJavaPlugins.setErrorHandler {
            Timber.tag(InjectHelper.context?.getString(R.string.app_name)).w(it)
        }
    }
}

class TimberLogTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(
            priority,
            "[${InjectHelper.context?.getString(R.string.app_name)}-${BuildConfig.VERSION_NAME}]",
            "$tag.$message",
            t
        )
    }
}
