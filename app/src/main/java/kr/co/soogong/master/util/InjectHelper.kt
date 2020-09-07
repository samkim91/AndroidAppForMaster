package kr.co.soogong.master.util

import android.content.Context
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import java.lang.ref.WeakReference

object InjectHelper {
    private var weakReferenceContext: WeakReference<Context?>? = null

    var context: Context?
        get() = weakReferenceContext?.get()
        @Synchronized
        set(context) {
            weakReferenceContext = WeakReference(context)
        }

    private var weakReferenceNetworkFlipperPlugin: WeakReference<NetworkFlipperPlugin?>? = null

    var networkFlipperPlugin: NetworkFlipperPlugin?
        get() = weakReferenceNetworkFlipperPlugin?.get()
        @Synchronized
        set(context) {
            weakReferenceNetworkFlipperPlugin = WeakReference(context)
        }
}