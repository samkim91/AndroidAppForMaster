@file:JvmName("ImageViewExt")

package kr.co.soogong.master.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside

@BindingAdapter("bind:image_url")
fun ImageView.setImageUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .transform(CenterInside())
        .into(this)
}