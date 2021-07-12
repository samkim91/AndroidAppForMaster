@file:JvmName("ImageViewExt")

package kr.co.soogong.master.utility.extension

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside

@BindingAdapter("bind:image_url")
fun ImageView.setImageUrl(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(it)
            .transform(CenterInside())
            .into(this)
    }
}

@BindingAdapter("bind:image_uri")
fun ImageView.setImageUri(uri: Uri?) {
    uri?.let {
        Glide.with(this.context)
            .load(it)
            .transform(CenterCrop())
            .into(this)
    }
}