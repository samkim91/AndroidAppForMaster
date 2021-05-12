@file:JvmName("RatingBarExt")

package kr.co.soogong.master.util.extension

import androidx.appcompat.widget.AppCompatRatingBar
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:rating")
fun AppCompatRatingBar.setRating(double: Double?) {
    rating = double?.toFloat() ?: 0.0f
}
