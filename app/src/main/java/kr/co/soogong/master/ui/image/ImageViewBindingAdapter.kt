package kr.co.soogong.master.ui.image

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import kr.co.soogong.master.data.dto.common.AttachmentDto

@BindingAdapter("bind:photo_list")
fun ViewPager2.setList(items: List<AttachmentDto>?) {
    (adapter as? ImageSliderAdapter)?.setList(items ?: emptyList())
}