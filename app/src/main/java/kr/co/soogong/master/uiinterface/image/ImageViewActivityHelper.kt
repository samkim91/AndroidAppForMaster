package kr.co.soogong.master.uiinterface.image

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.image.ImageActivity

object ImageViewActivityHelper {
    const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_IMAGE_ACTIVITY"
    private const val BUNDLE_KEY_ESTIMATION_ID = "BUNDLE_KEY_ESTIMATION_ID"
    private const val BUNDLE_KEY_IMAGE_POSITION = "BUNDLE_KEY_IMAGE_POSITION"

    fun getIntent(context: Context, keycode: String, position: Int = 0): Intent {
        return Intent(context, ImageActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putString(BUNDLE_KEY_ESTIMATION_ID, keycode)
                putInt(BUNDLE_KEY_IMAGE_POSITION, position)
            })
        }
    }

    fun getEstimationId(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getString(BUNDLE_KEY_ESTIMATION_ID) ?: ""
    }

    fun getImagePosition(intent: Intent): Int {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getInt(BUNDLE_KEY_IMAGE_POSITION) ?: 0
    }
}