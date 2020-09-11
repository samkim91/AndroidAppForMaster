package kr.co.soogong.master.ui.requirements.progress.detail

import android.os.Bundle
import androidx.activity.viewModels
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityProgressDetailBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.uiinterface.requirments.progress.detail.ProgressDetailActivityHelper
import timber.log.Timber

class ProgressDetailActivity : BaseActivity<ActivityProgressDetailBinding>(
    R.layout.activity_progress_detail
) {
    private val keycode by lazy {
        intent.getBundleExtra(ProgressDetailActivityHelper.EXTRA_KEY_BUNDLE)
            ?.getString(ProgressDetailActivityHelper.BUNDLE_KEY_RECEIVED_KEY, "") ?: ""
    }

    private val viewModel: ProgressDetailViewModel by viewModels {
        ProgressDetailViewModelFactory(getRepository(this), keycode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(TAG).d("onCreate: ")
        super.onCreate(savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@ProgressDetailActivity
            actionBar.apply {
                title.text = "견적서"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }
    }

    companion object {
        private const val TAG = "ProgressDetailActivity"
    }
}