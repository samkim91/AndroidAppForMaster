package kr.co.soogong.master.util

import android.content.Context
import java.lang.ref.WeakReference

object ContextHelper {
    private var weakReference: WeakReference<Context?>? = null

    var context: Context?
        get() = weakReference?.get()
        @Synchronized
        set(context) {
            weakReference = WeakReference(context)
        }
}