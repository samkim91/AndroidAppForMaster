package kr.co.soogong.master.presentation.uihelper.profile

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.presentation.ui.profile.review.MyReviewsActivity

object MyReviewsActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, MyReviewsActivity::class.java)
    }
}
