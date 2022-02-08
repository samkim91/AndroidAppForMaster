package kr.co.soogong.master.presentation.uihelper.profile

import android.content.Intent
import android.net.Uri
import kr.co.soogong.master.contract.HttpContract

object ShowMyProfileInWebHelper {
    fun getIntent(uid: String?): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(HttpContract.MY_PAGE_URL + uid))
    }
}
