@file:JvmName("ImageViewExt")

package kr.co.soogong.master.utility.extension

import android.net.Uri
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kr.co.soogong.master.R

@BindingAdapter("bind:image_url")
fun ImageView.setImageUrl(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(this)
    }
}

@BindingAdapter("bind:image_uri")
fun ImageView.setImageUri(uri: Uri?) {
    uri?.let {
        Glide.with(this.context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(this)
    }
}

fun ImageView.startHalfRotateAnimation(origin: Boolean) {
    startAnimation(
        AnimationUtils.loadAnimation(
            context,
            if (origin) R.anim.rotate_half_clockwise else R.anim.rotate_half_more_clockwise
        ).apply { fillAfter = true })
}