@file:JvmName("ImageViewExt")

package kr.co.soogong.master.util.extension

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside

@BindingAdapter("bind:image_url")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .transform(CenterInside())
        .into(this)

//    Glide.with(this.context)
//        .load(url)
//        .error(R.drawable.ic_camera) // 에러 있을 때 기본으로 변경할 수 있도록..
//        .transform(CenterInside())
//        .into(this)
}

@BindingAdapter("bind:image_uri")
fun ImageView.setImage(uri: Uri?) {
    Glide.with(this.context)
        .load(uri)
        .transform(CenterCrop())
        .into(this)
}