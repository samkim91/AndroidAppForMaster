package kr.co.soogong.master.utility.extension

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEachIndexed
import com.google.android.material.tabs.TabLayout

fun TabLayout.setTabClickListener(
    onSelected: (TabLayout.Tab?) -> Unit,
    onUnselected: (TabLayout.Tab?) -> Unit,
) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            onSelected(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            onUnselected(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }
    })
}

// 참고 : https://sweetcoding.tistory.com/168
fun TabLayout.changeTabFont(position: Int?, style: Int) {
    (this.getChildAt(0) as ViewGroup).let { viewGroup ->
        for (i in 0 until viewGroup.childCount) {
            (viewGroup.getChildAt(i) as ViewGroup).let { viewGroupTab ->
                viewGroupTab.forEachIndexed { index, _ ->
                    (viewGroupTab.getChildAt(index)).let { tabViewChild ->
                        if (tabViewChild is TextView && i == position) {
                            tabViewChild.setTextAppearance(style)
                        }
                    }
                }
            }
        }
    }
}
