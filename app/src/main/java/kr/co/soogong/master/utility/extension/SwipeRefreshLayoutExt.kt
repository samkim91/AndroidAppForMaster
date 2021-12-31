@file:JvmName("SwipeRefreshLayoutExt")

package kr.co.soogong.master.utility.extension

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kr.co.soogong.master.R

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.onRefresh(refresh: () -> Unit) {
    setOnRefreshListener {
        refresh()
        isRefreshing = false
    }
}

@BindingAdapter("setLoadingColor")
fun SwipeRefreshLayout.setLoadingColor(color: Int) {
    setColorSchemeResources(R.color.app_color)
}
