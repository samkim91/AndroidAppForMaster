package kr.co.soogong.master.ui.image

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.soogong.master.data.dto.common.AttachmentDto

class ImagePagerAdapter(
    activity: FragmentActivity,
    private val images: List<AttachmentDto>,
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = images.count()

    override fun createFragment(position: Int): Fragment =
        ImageFragment.newInstance(images[position])
}