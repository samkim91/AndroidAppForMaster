package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipeableViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    var swipEnabled = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (swipEnabled) {
            return super.onTouchEvent(ev)
        }
        return false;
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (swipEnabled) {
            return super.onInterceptTouchEvent(ev)
        }
        return false
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, false)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}