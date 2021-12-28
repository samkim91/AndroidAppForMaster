package kr.co.soogong.master.utility

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.utility.extension.dp

class SpaceItemDecoration(
    private val startSpace: Int?,
    private val topSpace: Int?,
    private val endSpace: Int?,
    private val bottomSpace: Int?,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        startSpace?.run { outRect.left = this.dp }
        topSpace?.run { outRect.top = this.dp }
        endSpace?.run { outRect.right = this.dp }
        bottomSpace?.run { outRect.bottom = this.dp }
    }
}