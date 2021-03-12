package kr.co.soogong.master.ui.image

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import kr.co.soogong.master.data.estimation.ImagePath

@BindingAdapter("bind:photo_list")
fun ViewPager2.setList(items: List<ImagePath>?) {
    (adapter as? ImageSliderAdapter)?.setList(items ?: emptyList())
}