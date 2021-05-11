package kr.co.soogong.master.uiinterface.profile

import android.content.Context
import android.content.Intent
import kr.co.soogong.master.ui.profile.review.MyReviewsActivity

object MyReviewsActivityHelper {
    fun getIntent(context: Context): Intent {
        return Intent(context, MyReviewsActivity::class.java)
    }
}
