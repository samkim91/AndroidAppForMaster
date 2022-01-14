@file:JvmName("ScrollViewExt")

package kr.co.soogong.master.utility.extension

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ScrollView
import androidx.core.animation.doOnEnd
import kotlin.math.abs

// 참고 : https://greedy0110.tistory.com/41
// 지정한 뷰까지 부드럽게 스크롤하는 함수
fun ScrollView.smoothScrollToView(
    view: View,
    marginTop: Int = 0,
    maxDuration: Long = 500L,
    onEnd: () -> Unit = {},
) {
    if (this.getChildAt(0).height <= this.height) {     // 이미 뷰가 상단에 있으면, 스크롤할 필요가 없음.
        onEnd()
        return
    }

    val y = computeDistanceToView(view) - marginTop
    val ratio = abs(y - this.scrollY) / (this.getChildAt(0).height - this.height).toFloat()
    ObjectAnimator.ofInt(this, "scrollY", y).apply {
        duration = (maxDuration * ratio).toLong()
        interpolator = AccelerateDecelerateInterpolator()
        doOnEnd {
            onEnd()
        }
        start()
    }
}

// 뷰까지 이동해야할 거리를 계산
internal fun ScrollView.computeDistanceToView(view: View): Int {
    return abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
}

// 스크린에서의 rect 를 계산
internal fun calculateRectOnScreen(view: View): Rect {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    return Rect(
        location[0],
        location[1],
        location[0] + view.measuredWidth,
        location[1] + view.measuredHeight
    )
}
