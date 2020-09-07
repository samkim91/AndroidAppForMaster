package kr.co.soogong.master.ui.requirements.received.detail

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityDetailBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.requirments.received.detail.DetailActivityHelper
import timber.log.Timber

class DetailActivity : BaseActivity<ActivityDetailBinding>(
    R.layout.activity_detail
) {

    private val receivedCardId by lazy {
        intent.getBundleExtra(DetailActivityHelper.EXTRA_KEY_BUNDLE)
            ?.getString(DetailActivityHelper.BUNDLE_KEY_RECEIVED_KEY) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {

    }

    companion object {
        private const val TAG = "DetailActivity"
    }
}