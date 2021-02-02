package kr.co.soogong.master

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import kr.co.soogong.master.util.InjectHelper
import leakcanary.AppWatcher
import timber.log.Timber

class DebugSoogongApp : SoogongApp() {

    override fun onCreate() {
        super.onCreate()

        AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = false)

        SoLoader.init(this, false)

        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)

            val descriptorMapping = DescriptorMapping.withDefaults()
            val networkPlugin = NetworkFlipperPlugin()
            val interceptor = FlipperOkhttpInterceptor(networkPlugin, true)

            client.addPlugin(InspectorFlipperPlugin(this, descriptorMapping))
            client.addPlugin(networkPlugin)
            client.addPlugin(
                SharedPreferencesFlipperPlugin(
                    this,
                    listOf(
                        SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor(
                            "soogong_shared_preferences",
                            MODE_PRIVATE
                        )
                    )
                )
            )
            client.addPlugin(NavigationFlipperPlugin.getInstance())
            client.addPlugin(CrashReporterPlugin.getInstance())
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.start()

            InjectHelper.interceptor = interceptor
        }

        Timber.tag("App").d("onCreate: Debug")
    }
}
