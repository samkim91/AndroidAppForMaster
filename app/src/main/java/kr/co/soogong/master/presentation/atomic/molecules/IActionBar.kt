package kr.co.soogong.master.presentation.atomic.molecules

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

interface IActionBar {

    val tvTitle: AppCompatTextView
    val tvRight: AppCompatTextView
    val ivBack: AppCompatImageView
    val ivIcon: AppCompatImageView

    var title: String?
    var backButtonVisibility: Boolean?
    var rightText: String?
    var icon: Drawable?

    fun setIvBackClickListener(listener: View.OnClickListener)
    fun setTvRightClickListener(listener: View.OnClickListener)
    fun setIvIconClickListener(listener: View.OnClickListener)
}