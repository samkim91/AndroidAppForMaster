package kr.co.soogong.master.ui.requirements.received.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityReceivedDetailBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.ui.requirements.received.estimate.EstimateActivity
import kr.co.soogong.master.uiinterface.requirments.received.detail.ReceivedDetailActivityHelper
import kr.co.soogong.master.uiinterface.requirments.received.estimate.EstimateActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class ReceivedDetailActivity : BaseActivity<ActivityReceivedDetailBinding>(
    R.layout.activity_received_detail
) {
    private val keycode by lazy {
        intent.getBundleExtra(ReceivedDetailActivityHelper.EXTRA_KEY_BUNDLE)
            ?.getString(ReceivedDetailActivityHelper.BUNDLE_KEY_RECEIVED_KEY, "") ?: ""
    }

    private val viewModel: ReceivedDetailViewModel by viewModels {
        ReceivedDetailViewModelFactory(getRepository(this), keycode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@ReceivedDetailActivity
            with(actionBar) {
                title.text = "수리요청서"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            setAcceptClick {
                viewModel.onClickedAccept()
            }

            setDeinedClick {
                viewModel.onClickedDenied()
            }
        }

        registerEventObserve()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.event.observe(this, EventObserver { event ->
            when (event) {
                ReceivedDetailViewModel.DENIED_EVENT -> {
                    Toast.makeText(this, "거절한 수리 문의 삭제가 완료되었습니다.", Toast.LENGTH_LONG).show()
                    finish()
                }

                ReceivedDetailViewModel.ACCEPT_EVENT -> {
                    this.run {
                        startActivity(Intent(this, EstimateActivity::class.java).apply {
                            putExtra(EstimateActivityHelper.EXTRA_KEY_BUNDLE, Bundle().apply {
                                putString(EstimateActivityHelper.BUNDLE_KEY_RECEIVED_KEY, keycode)
                            })
                        })
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "DetailActivity"
    }
}