@file:JvmName("ImageViewExt")

package kr.co.soogong.master.utility.extension

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kr.co.soogong.master.R

@BindingAdapter("bind:image_url")
fun ImageView.setImageUrl(url: String?) {
    if (url.isNullOrEmpty()) {
        Glide.with(this.context)
            .load(R.drawable.ic_empty_image)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(this)
        return
    }

    Glide.with(this.context)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(16))
        .into(this)
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

@BindingAdapter("setCircleImageByUrl")
fun ImageView.setCircleImageByUrl(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(
                if (it.isEmpty()) R.drawable.ic_empty_image else it
            )
            .transform(CenterCrop(), RoundedCorners(40))
            .into(this)
    }
}