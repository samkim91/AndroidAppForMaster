package kr.co.soogong.master.presentation.ui.common.image

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImagePagerAdapter(
    activity: FragmentActivity,
    private val images: List<String>,
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = images.count()

    override fun createFragment(position: Int): Fragment =
        ImageFragment.newInstance(images[position])
}