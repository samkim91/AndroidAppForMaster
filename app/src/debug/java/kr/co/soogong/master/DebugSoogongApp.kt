package kr.co.soogong.master

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import timber.log.Timber

class DebugSoogongApp : SoogongApp() {
    override fun onCreate() {
        super.onCreate()

        SoLoader.init(this, false)

        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)

            val descriptorMapping = DescriptorMapping.withDefaults()

            client.addPlugin(InspectorFlipperPlugin(this, descriptorMapping))
            client.addPlugin(NetworkFlipperPlugin())
            client.addPlugin(
                SharedPreferencesFlipperPlugin(
                    this,
                    listOf(
                        SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor(
                            "soogong_shared_preferences.pref",
                            MODE_PRIVATE
                        )
                    )
                )
            )
            client.addPlugin(NavigationFlipperPlugin.getInstance())
            client.addPlugin(CrashReporterPlugin.getInstance())
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.start()
        }

        Timber.tag("App").d("onCreate: Debug")
    }
}
