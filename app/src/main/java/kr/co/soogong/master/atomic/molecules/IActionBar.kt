package kr.co.soogong.master.atomic.molecules

import android.view.View

interface IActionBar {

    var title: String?
    var backButtonVisibility: Boolean?
    var anyButtonText: String?

    fun setButtonBackClickListener(listener: View.OnClickListener)
    fun setButtonAnyClickListener(listener: View.OnClickListener)
}