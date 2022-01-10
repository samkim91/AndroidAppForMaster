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
    Glide.with(this.context)
        .load(if (url.isNullOrEmpty()) R.drawable.ic_empty_image else url)
        .transform(CenterCrop(), RoundedCorners(8.dp))
        .into(this)
}

@BindingAdapter("bind:image_uri")
fun ImageView.setImageUri(uri: Uri?) {
    uri?.let {
        Glide.with(this.context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(8.dp))
            .into(this)
    }
}

@BindingAdapter("setCircleImageByUrl")
fun ImageView.setCircleImageByUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .into(this)
}