package kr.co.soogong.master.presentation.uihelper.common.image

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import kr.co.soogong.master.presentation.ui.common.image.ImageActivity

object ImageViewActivityHelper {
    private const val EXTRA_KEY_BUNDLE = "EXTRA_KEY_BUNDLE_IMAGE_ACTIVITY"
    private const val BUNDLE_KEY_IMAGE_POSITION = "BUNDLE_KEY_IMAGE_POSITION"
    private const val BUNDLE_KEY_IMAGES = "BUNDLE_KEY_IMAGES"

    // Intent 에 attachments 를 parse 하기 위해, mutableList 를 사용
    fun getIntent(
        context: Context,
        images: List<String>,
        position: Int = 0,
    ): Intent {
        return Intent(context, ImageActivity::class.java).apply {
            putExtra(EXTRA_KEY_BUNDLE, bundleOf(
                BUNDLE_KEY_IMAGE_POSITION to position,
                BUNDLE_KEY_IMAGES to images
            ))
        }
    }

    fun getImagePosition(intent: Intent): Int {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getInt(BUNDLE_KEY_IMAGE_POSITION) ?: 0
    }

    fun getImages(intent: Intent): List<String> {
        return intent.getBundleExtra(EXTRA_KEY_BUNDLE)?.getStringArrayList(BUNDLE_KEY_IMAGES)
            ?: emptyList()
    }
}