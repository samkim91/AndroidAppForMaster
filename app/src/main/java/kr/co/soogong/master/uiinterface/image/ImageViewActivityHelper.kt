package kr.co.soogong.master.uiinterface.image

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.ui.image.ImageActivity

object ImageViewActivityHelper {
    private const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_IMAGE_ACTIVITY"
    private const val BUNDLE_KEY_IMAGE_POSITION = "BUNDLE_KEY_IMAGE_POSITION"
    private const val BUNDLE_KEY_IMAGES = "BUNDLE_KEY_IMAGES"

    fun getIntent(context: Context, images: ArrayList<ImagePath>?, position: Int = 0): Intent {
        return Intent(context, ImageActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, Bundle().apply {
                putInt(BUNDLE_KEY_IMAGE_POSITION, position)
                putParcelableArrayList(BUNDLE_KEY_IMAGES, images)
            })
        }
    }

    fun getImagePosition(intent: Intent): Int {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getInt(BUNDLE_KEY_IMAGE_POSITION) ?: 0
    }

    fun getImages(intent: Intent): ArrayList<ImagePath> {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getParcelableArrayList(
            BUNDLE_KEY_IMAGES) ?: ArrayList()
    }
}